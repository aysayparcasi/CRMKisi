package entity;

import lombok.*;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "abcd")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Kisi {

    @Id
    @SequenceGenerator(name = "seq_kisi",allocationSize = 1)
    @GeneratedValue(generator = "seq_kisi" , strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 100, name = "adi")
    private String adi;
    @Column(length = 100, name = "soyadi")
    private String soyadi;

    @OneToMany
    @JoinColumn(name = "kisi_adres_id")
    private List<Adress> adresleri;  //join işlemlerine başladım.

    public Kisi() {

    }
}
