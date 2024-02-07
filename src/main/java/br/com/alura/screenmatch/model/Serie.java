package br.com.alura.screenmatch.model;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacaoImdb;
    private Categoria genero;
    private String atores;
    private String poste;
    private String sinopse;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();

        try{
            this.avaliacaoImdb = Double.valueOf(dadosSerie.avaliacaoImdb());
        }
        catch (NumberFormatException e) {
            this.avaliacaoImdb = 0.0;
        }

        this.genero = Categoria.fromPortugues(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poste = dadosSerie.poster();
        this.sinopse = Tradutor.traduzirInglesParaPortugues(dadosSerie.sinopse());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacaoImdb() {
        return avaliacaoImdb;
    }

    public void setAvaliacaoImdb(Double avaliacaoImdb) {
        this.avaliacaoImdb = avaliacaoImdb;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return  "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacaoImdb=" + avaliacaoImdb +
                ", atores='" + atores + '\'' +
                ", poste='" + poste + '\'' +
                ", sinopse='" + sinopse + '\'';
    }
}
