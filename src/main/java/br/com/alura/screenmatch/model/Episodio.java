package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer episodio;
    private Double avaliacaoImdb;
    private LocalDate dataLancamento;
    @ManyToOne
    private Serie serie;

    public Episodio() {}

    public Episodio(Integer temporada, DadosEpisodios dadosEpisodios) {
        this.temporada = temporada;
        this.titulo = dadosEpisodios.titulo();
        this.episodio = dadosEpisodios.episodio();

        try{
            this.avaliacaoImdb = Double.valueOf(dadosEpisodios.avaliacaoImdb());
        } catch (NumberFormatException e) {
            this.avaliacaoImdb = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        } catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Double getAvaliacaoImdb() {
        return avaliacaoImdb;
    }

    public void setAvaliacaoImdb(Double avaliacaoImdb) {
        this.avaliacaoImdb = avaliacaoImdb;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", episodio=" + episodio +
                ", avaliacaoImdb=" + avaliacaoImdb +
                ", dataLancamento=" + dataLancamento;
    }
}
