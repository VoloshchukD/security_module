package com.epam.esm.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "certificate_tag_maps")
public class CertificateTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate certificate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateTagMap that = (CertificateTagMap) o;
        return Objects.equals(id, that.id) && Objects.equals(certificate, that.certificate)
                && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificate, tag);
    }

    @Override
    public String toString() {
        return "CertificateTagMap{" +
                "id=" + id +
                ", certificate=" + certificate +
                ", tag=" + tag +
                '}';
    }

}
