package com.matus.cursomc.dto;

import com.matus.cursomc.domain.Cidade;

import java.io.Serializable;

public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomem;

    public CidadeDTO(){

    }

    public CidadeDTO(Cidade obj){
        this.id = obj.getId();
        this.nomem = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomem() {
        return nomem;
    }

    public void setNomem(String nomem) {
        this.nomem = nomem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CidadeDTO{");
        sb.append("id=").append(id);
        sb.append(", nomem='").append(nomem).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
