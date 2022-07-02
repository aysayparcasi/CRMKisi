package impl;

import dto.KisiDto;
import entity.Adress;
import entity.Kisi;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repo.AdresRepository;
import repo.KisiRepository;
import service.KisiService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KisiServiceImpl implements KisiService {

    private final KisiRepository kisiRepository;
    private final AdresRepository adresRepository;
    @Override
    @Transactional
    public KisiDto save(KisiDto kisiDto) {
       // Assert.isNull(kisiDto.getAdi(), "Adi zorunludur"); //exception atsın diye geriye
        Kisi kisi = new Kisi();
        kisi.setAdi(kisiDto.getAdi());
        kisi.setSoyadi(kisiDto.getSoyadi());
        final Kisi kisiDB= kisiRepository.save(kisi);
        List<Adress> liste =new  ArrayList<>();
        kisiDto.getAdresler().forEach(item -> {
            Adress adress = new Adress();
            adress.setAdres(item);
            adress.setAdresTip(Adress.AdresTip.DIGER);
            adress.setAktif(true);
            adress.setKisi(kisiDB);
            liste.add(adress);
        });
        adresRepository.saveAll(liste);
        kisiDto.setId(kisiDB.getId());
        return kisiDto;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<KisiDto> getAll() {
        List<Kisi> kisiler = kisiRepository.findAll();
        List<KisiDto> kisiDtos = new ArrayList<>();
        kisiler.forEach(it ->{
            KisiDto kisiDto = new KisiDto();
            kisiDto.setId(it.getId());
            kisiDto.setAdi(it.getAdi());
            kisiDto.setSoyadi(it.getSoyadi());
            kisiDto.setAdresler(it.getAdresleri()
                    .stream().map(Adress::getAdres)
                    .collect(Collectors.toList()));
            kisiDtos.add(kisiDto);
        });

        return kisiDtos;
    }

    @Override
    public Page<KisiDto> getAll(Pageable pageable) {
        return null;
    }
}
