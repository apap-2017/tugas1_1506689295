package com.example.tp1.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tp1.model.KeluargaModel;
import com.example.tp1.model.PendudukModel;
import com.example.tp1.model.KelurahanModel;
import com.example.tp1.model.KecamatanModel;
import com.example.tp1.model.KotaModel;
import com.example.tp1.service.KeluargaService;
import com.example.tp1.service.PendudukService;
import com.example.tp1.service.KelurahanService;
import com.example.tp1.service.KecamatanService;
import com.example.tp1.service.KotaService;

@Controller
public class AllController 
{
	@Autowired
    PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@Autowired
	KecamatanService kecamatanDAO;
	
	long id_keluarga_former;
	
	@RequestMapping("/")
    public String index (Model model)
    {
		model.addAttribute("page_title", "Home");
        return "index";
    }
	
	@RequestMapping("/penduduk/tambah")
    public String goToAddPenduduk(Model model)
    {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("page_title", "Add Penduduk");
        return "add_penduduk_form";
    }
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String addPenduduk(PendudukModel pendudukBaru, Model model)
    {	
		String pekerjaan = pendudukBaru.getPekerjaan().toUpperCase();
		pendudukBaru.setPekerjaan(pekerjaan);
		
		int kelamin = pendudukBaru.getJenis_kelamin();
		
		//cari kode tanggal lahir
		String tanggal_lahir = pendudukBaru.getTanggal_lahir();
		String[] splitTanggal = tanggal_lahir.split("-");
		
		//ini kode tanggal lahir
		int tanggal = Integer.parseInt(splitTanggal[2]);
		if(kelamin == 1) {
			tanggal+=40;
		}
		String trueTanggal = String.format("%02d", tanggal);
		
    	String bulan = splitTanggal[1];
    	String tahun = splitTanggal[0];
    	String trueTahun = tahun.substring(2, 4);
    	String kodeLahir = trueTanggal+bulan+trueTahun;
    	
    	KeluargaModel keluargaLama = keluargaDAO.getKeluargaId(pendudukBaru.getId_keluarga());
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluargaLama.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getId_kecamatan());
		String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0,6);
		
		int counter = 1;
		String belakang = String.format("%04d", counter);
		
		String toFindNik = kodeKecamatan + kodeLahir;
		String trueNik = "";
		
		PendudukModel pendudukLama = pendudukDAO.getNik(toFindNik);
		
		
		if(kelamin == 1) {
			if(pendudukLama == null) {
				trueNik = toFindNik + belakang;
				pendudukBaru.setNik(trueNik);
				pendudukDAO.addPenduduk(pendudukBaru);
			}else {
				if(pendudukLama.getJenis_kelamin() == 1) {
		    		String nikLama = pendudukLama.getNik().substring(12, 16);
		    		int indexLama = Integer.parseInt(nikLama);
		    		String nikTemp = toFindNik.substring(0, 12);
		    		
		    		counter = indexLama + 1;
		    		String lastIndex = String.format("%04d", counter);
		    		trueNik = nikTemp + lastIndex;
		    		pendudukBaru.setNik(trueNik);
					pendudukDAO.addPenduduk(pendudukBaru);	
				}else {
					tanggal = tanggal - 40;
					String kodeLahir2 = tanggal+bulan+trueTahun;
					trueNik = kodeKecamatan + kodeLahir2 + belakang;
					pendudukDAO.addPenduduk(pendudukBaru);
				}
			}
		}else{
			if(pendudukLama == null) {
				trueNik = toFindNik + belakang;
				pendudukBaru.setNik(trueNik);
				pendudukDAO.addPenduduk(pendudukBaru);
			}else {
	    		String nikLama = pendudukLama.getNik().substring(12, 16);
	    		int indexLama = Integer.parseInt(nikLama);
	    		String nikTemp = toFindNik.substring(0, 12);
	    		
	    		counter = indexLama + 1;
	    		String lastIndex = String.format("%04d", counter);
	    		trueNik = nikTemp + lastIndex;
	    		pendudukBaru.setNik(trueNik);
				pendudukDAO.addPenduduk(pendudukBaru);
				}	
		}
	
		model.addAttribute("penduduknik", pendudukBaru.getNik());
    	model.addAttribute("page_title", "Add Success");
    	
    	return "success_add_penduduk";
    }
	
	@PostMapping(value = "/penduduk/mati")
    public String pendudukDeath(PendudukModel pendudukToDeath, Model model)
    {
		if(pendudukToDeath.getIs_wafat() == 1) {
			return "already_dead";
		}else {
			id_keluarga_former = pendudukToDeath.getId_keluarga();
			KeluargaModel keluarga = keluargaDAO.getKeluargaId(pendudukToDeath.getId_keluarga());
			pendudukDAO.setPendudukDeath(pendudukToDeath);
			KeluargaModel keluargaList = keluargaDAO.selectKeluarga(keluarga.getNomor_kk());
			
			if(keluargaList.getAnggota_keluarga().isEmpty()) {
				keluargaDAO.updateStatus(keluarga.getNomor_kk());
			}
			
			model.addAttribute("penduduk", pendudukToDeath);
			model.addAttribute("nik_death", pendudukToDeath.getNik());
			model.addAttribute("page_title", "Death Success");
			return "success_death_penduduk";
		}
		
    }
	
	@RequestMapping("/penduduk")
    public String viewPenduduk (@RequestParam(value = "nik", required = false) String nik, Model model)
    {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		
		KeluargaModel keluarga;
		if(penduduk.getId_keluarga() == 0) {
			keluarga = keluargaDAO.getKeluargaId(id_keluarga_former);
		}else {
			keluarga = keluargaDAO.getKeluargaId(penduduk.getId_keluarga());
		}
		
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.getKota(kecamatan.getId_kota());
		penduduk.setAlamat(keluarga.getAlamat());
		penduduk.setRt(keluarga.getRt());
		penduduk.setRw(keluarga.getRw());
		penduduk.setNama_kelurahan(kelurahan.getNama_kelurahan());
		penduduk.setNama_kecamatan(kecamatan.getNama_kecamatan());
		penduduk.setNama_kota(kota.getNama_kota());

    	String tanggal_lahir = penduduk.getTanggal_lahir();
    	String tanggal = tanggal_lahir.substring(8,10);
    	String bulan = tanggal_lahir.substring(5,7);
    	String tahun = tanggal_lahir.substring(0,4);

    	if(bulan.equals("01")) {
    		bulan = "Januari";
    	}else if(bulan.equals("02")){
    		bulan = "Februari";
    	}else if(bulan.equals("03")) {
    		bulan = "Maret";
    	}else if(bulan.equals("04")) {
    		bulan = "April";
    	}else if(bulan.equals("05")) {
    		bulan = "Mei";
    	}else if(bulan.equals("06")) {
    		bulan = "Juni";
    	}else if(bulan.equals("07")) {
    		bulan = "Juli";
    	}else if(bulan.equals("08")) {
    		bulan = "Agustus";
    	}else if(bulan.equals("09")) {
    		bulan = "September";
    	}else if(bulan.equals("10")) {
    		bulan = "Oktober";
    	}else if(bulan.equals("11")) {
    		bulan = "November";
    	}else if(bulan.equals("12")) {
    		bulan = "Desember";
    	}
    	
    	String hasil_date = tanggal + " " + bulan + " " + tahun;
    	penduduk.setTanggal_lahir(hasil_date);
    	
    	model.addAttribute("penduduk", penduduk);
    	model.addAttribute("page_title", "View Penduduk");
    	return "view_penduduk";
    }
	
	
	
	@GetMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(@PathVariable(value = "nik") String nik, Model model)
    {
		PendudukModel pendudukLama = pendudukDAO.getPenduduk(nik);
		String kode_kecamatan = pendudukLama.getNik().substring(0,6) + "0";
		String currentNik = pendudukLama.getNik();
		pendudukLama.setNik(currentNik);
	
		String nama_kecamatan = kecamatanDAO.selectNamaKecamatan(kode_kecamatan);
		pendudukLama.setNama_kecamatan(nama_kecamatan);
		
		model.addAttribute("nik", nik);
		model.addAttribute("penduduk", pendudukLama);
		model.addAttribute("page_title", "Update Penduduk");
		return "update_penduduk_form";
    }
	
	@PostMapping(value = "/penduduk/ubah/{nik}")
    public String updatePendudukSubmit(@PathVariable(value = "nik") String nik, 
    		PendudukModel pendudukToUpdate, Model model)
    {
		String nikAwal = pendudukToUpdate.getNik();
		String currentNik = pendudukToUpdate.getNik().substring(0, 12);
		String pekerjaan = pendudukToUpdate.getPekerjaan().toUpperCase();
		pendudukToUpdate.setPekerjaan(pekerjaan);
		
		int kelamin = pendudukToUpdate.getJenis_kelamin();
		
		
		//cari kode tanggal lahir
		String tanggal_lahir = pendudukToUpdate.getTanggal_lahir();
		String[] splitTanggal = tanggal_lahir.split("-");
		
		//ini kode tanggal lahir
		int tanggal = Integer.parseInt(splitTanggal[2]);
		if(kelamin == 1) {
			tanggal+=40;
		}
		String trueTanggal = String.format("%02d", tanggal);
		
    	String bulan = splitTanggal[1];
    	String tahun = splitTanggal[0];
    	String trueTahun = tahun.substring(2, 4);
    	String kodeLahir = trueTanggal+bulan+trueTahun;
    	
    	KeluargaModel keluargaLama = keluargaDAO.getKeluargaId(pendudukToUpdate.getId_keluarga());
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluargaLama.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getId_kecamatan());
		String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0,6);
		
		
		int counter = 1;
		String belakang = String.format("%04d", counter);
		
		String toFindNik = kodeKecamatan + kodeLahir;
		
		String trueNik = "";
		
		if(toFindNik.equals(currentNik)) {
			pendudukToUpdate.setNik(nikAwal);
			trueNik = nikAwal;
			pendudukDAO.updatePenduduk(pendudukToUpdate);
		}else {
			
			PendudukModel pendudukLama = pendudukDAO.getNik(toFindNik);
			
			if(kelamin == 1) {
				if(pendudukLama == null) {
					trueNik = toFindNik + belakang;
					pendudukToUpdate.setNik(trueNik);
					pendudukDAO.updatePenduduk(pendudukToUpdate);
				}else {
					if(pendudukLama.getJenis_kelamin() == 1) {
			    		String nikLama = pendudukLama.getNik().substring(12, 16);
			    		int indexLama = Integer.parseInt(nikLama);
			    		String nikTemp = toFindNik.substring(0, 12);
			    		
			    		counter = indexLama + 1;
			    		String lastIndex = String.format("%04d", counter);
			    		trueNik = nikTemp + lastIndex;
			    		pendudukToUpdate.setNik(trueNik);
			    		pendudukDAO.updatePenduduk(pendudukToUpdate);	
					}else {
						tanggal = tanggal - 40;
						String kodeLahir2 = tanggal+bulan+trueTahun;
						trueNik = kodeKecamatan + kodeLahir2 + belakang;
						pendudukDAO.updatePenduduk(pendudukToUpdate);
					}
				}
			}else{
				if(pendudukLama == null) {
					trueNik = toFindNik + belakang;
					pendudukToUpdate.setNik(trueNik);
					pendudukDAO.updatePenduduk(pendudukToUpdate);
				}else {
		    		String nikLama = pendudukLama.getNik().substring(12, 16);
		    		int indexLama = Integer.parseInt(nikLama);
		    		String nikTemp = toFindNik.substring(0, 12);
		    		
		    		counter = indexLama + 1;
		    		String lastIndex = String.format("%04d", counter);
		    		trueNik = nikTemp + lastIndex;
		    		pendudukToUpdate.setNik(trueNik);
		    		pendudukDAO.updatePenduduk(pendudukToUpdate);
					}	
			}
		}
		model.addAttribute("nik_changed", nikAwal);
		model.addAttribute("nik_now", trueNik);
    	model.addAttribute("page_title", "Update Success");
		
		return "success_update_penduduk";
    }
	
	
	@RequestMapping("/keluarga")
    public String viewKeluarga (
            @RequestParam(value = "nkk", required = false) String nkk,
			Model model)
    {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);		
    	model.addAttribute("page_title", "View Keluarga");
    	model.addAttribute("keluarga", keluarga);
    	return "view_keluarga";
    }
	
	@RequestMapping("/keluarga/tambah")
    public String goToAddKeluarga(Model model)
    {
		KeluargaModel keluarga = new KeluargaModel();
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("page_title", "Add Keluarga");
        return "add_keluarga_form";
    }
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String addKeluarga(KeluargaModel keluargaBaru, Model model)
    {	
		//cari dan set id kelurahan
		String nama_kelurahan = keluargaBaru.getNama_kelurahan().toUpperCase();
		String id_kelurahan = kelurahanDAO.selectIdKelurahan(nama_kelurahan);
		keluargaBaru.setId_kelurahan(id_kelurahan);
		keluargaBaru.setIs_tidak_berlaku(0);
		
		//cari id kecamatan
		String namaKecamatan = keluargaBaru.getNama_kecamatan().toUpperCase();
		String kodeKec = kecamatanDAO.selectKodeKecamatan(namaKecamatan);
		String kodeKecamatan = kodeKec.substring(0,6);
		
		//get 6 digit tanggal
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String nkkDate = dtf.format(localDate);
		String tanggal = nkkDate.substring(8,10);
    	String bulan = nkkDate.substring(5,7);
    	String tahun = nkkDate.substring(2,4);
    	String kodeTanggal = tanggal + bulan + tahun;
    	int rt = Integer.parseInt(keluargaBaru.getRt());
    	keluargaBaru.setRt(String.format("%03d", rt));
    	
    	int rw = Integer.parseInt(keluargaBaru.getRw());
    	keluargaBaru.setRw(String.format("%03d", rw));
    
  
		String toFindNkk = kodeKecamatan + kodeTanggal;
		String trueNkk = kodeKecamatan + kodeTanggal + "0001";
		
		KeluargaModel keluargaLama = keluargaDAO.selectKeluargaIni(toFindNkk);
    	
    	if(keluargaLama == null) {
    		keluargaBaru.setNomor_kk(trueNkk);
    		keluargaDAO.addKeluarga(keluargaBaru);
    	}else {
    		String nkkLama = keluargaLama.getNomor_kk().substring(12, 16);
    		int indexLama = Integer.parseInt(nkkLama);
    		String nkkTemp = keluargaLama.getNomor_kk().substring(0, 12);
    		
    		int counter = indexLama + 1;
    		String lastIndex = String.format("%04d", counter);
    		trueNkk = nkkTemp + lastIndex;
    		keluargaBaru.setNomor_kk(trueNkk);
    		keluargaDAO.addKeluarga(keluargaBaru);
    	}
    	
    	model.addAttribute("keluargaBaru", trueNkk);
    	model.addAttribute("page_title", "Add Keluarga Berhasil");
		return "success_add_keluarga";
    }
	
	@GetMapping("/keluarga/ubah/{nomor_kk}")
    public String updateKeluarga(@PathVariable(value = "nomor_kk") String nomor_kk, Model model)
    {
		KeluargaModel keluargaLama = keluargaDAO.getKeluarga(nomor_kk);
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluargaLama.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = kotaDAO.getKota(kecamatan.getId_kota());
		keluargaLama.setNama_kelurahan(kelurahan.getNama_kelurahan());
		keluargaLama.setNama_kecamatan(kecamatan.getNama_kecamatan());
		keluargaLama.setNama_kota(kota.getNama_kota());
		
		model.addAttribute("nomor_kk", nomor_kk);
		
		model.addAttribute("keluarga", keluargaLama);
		model.addAttribute("page_title", "Update keluarga");
		return "update_keluarga_form";
    }
	
	@PostMapping(value = "/keluarga/ubah/{nomor_kk}")
    public String updateKeluargaSubmit(@PathVariable(value = "nomor_kk") String nomor_kk, 
    		KeluargaModel keluargaToUpdate, Model model)
    {
		String nkkAwal = keluargaToUpdate.getNomor_kk();
		String currentNkk = nkkAwal.substring(0, 12);

		//cari dan set id kelurahan
		String nama_kelurahan = keluargaToUpdate.getNama_kelurahan().toUpperCase();
		String id_kelurahan = kelurahanDAO.selectIdKelurahan(nama_kelurahan);
		keluargaToUpdate.setId_kelurahan(id_kelurahan);
		keluargaToUpdate.setIs_tidak_berlaku(0);
		
		//cari id kecamatan
		String namaKecamatan = keluargaToUpdate.getNama_kecamatan().toUpperCase();
		String kodeKec = kecamatanDAO.selectKodeKecamatan(namaKecamatan);
		String kodeKecamatan = kodeKec.substring(0,6);
		
		//get 6 digit tanggal
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String nkkDate = dtf.format(localDate);
		String tanggal = nkkDate.substring(8,10);
    	String bulan = nkkDate.substring(5,7);
    	String tahun = nkkDate.substring(2,4);
    	String kodeTanggal = tanggal + bulan + tahun;
    	int rt = Integer.parseInt(keluargaToUpdate.getRt());
    	keluargaToUpdate.setRt(String.format("%03d", rt));
    	
    	int rw = Integer.parseInt(keluargaToUpdate.getRw());
    	keluargaToUpdate.setRw(String.format("%03d", rw));
  
		String toFindNkk = kodeKecamatan + kodeTanggal;
		
		String trueNkk = "";
		
		if(toFindNkk.equals(currentNkk)) {
			keluargaToUpdate.setNomor_kk(nkkAwal);
			trueNkk = nkkAwal;
			keluargaDAO.updateKeluarga(keluargaToUpdate);
		}else {
			KeluargaModel keluargaLama = keluargaDAO.selectKeluargaIni(toFindNkk);
			
			if(keluargaLama == null) {
				keluargaToUpdate.setNomor_kk(trueNkk);
				keluargaDAO.updateKeluarga(keluargaToUpdate);
	    	}else {
	    		String nkkLama = keluargaLama.getNomor_kk().substring(12, 16);
	    		int indexLama = Integer.parseInt(nkkLama);
	    		String nkkTemp = keluargaLama.getNomor_kk().substring(0, 12);
	    		
	    		int counter = indexLama + 1;
	    		String lastIndex = String.format("%04d", counter);
	    		trueNkk = nkkTemp + lastIndex;
	    		keluargaToUpdate.setNomor_kk(trueNkk);
	    		keluargaDAO.updateKeluarga(keluargaToUpdate);
	    	}
		}
		model.addAttribute("nkk_changed", nkkAwal);
		model.addAttribute("nkk_now", trueNkk);
    	model.addAttribute("page_title", "Update Success");
		
		return "success_update_keluarga";
    }
	
    
}
