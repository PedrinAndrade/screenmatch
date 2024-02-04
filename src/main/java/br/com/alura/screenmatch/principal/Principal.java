package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi api = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=75247e2e";

    public void exibirMenu() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeDaSerie = scanner.nextLine();
        var json = api.obterDados(ENDERECO + nomeDaSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = api.obterDados(ENDERECO + nomeDaSerie.replace(" ", "+")
                    + "&season=" + i + API_KEY);
            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
            System.out.println(dadosTemporadas);
            temporadas.add(dadosTemporadas);
        }
        temporadas.forEach(System.out::println);
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 10 episódios com melhor IMDb: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacaoImdb().equalsIgnoreCase("n/a"))
                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacaoImdb).reversed())
                .peek(e -> System.out.println("Ordenação decrescente " + e))
                .limit(10)
                .peek(e -> System.out.println("Limitando a 10 " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeando os títulos e transformando para letra Maiuscula " + e))
                .forEach(System.out::println);

        System.out.println();
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.println("Informe o nome do episódio que deseja buscar: ");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> episodioPesquisado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if(episodioPesquisado.isPresent()) {
            System.out.println("Episódio encontrado");
            System.out.println("Temporada: " + episodioPesquisado.get().getTemporada());
        } else {
            System.out.println("Nenhum episódio encontrado com esse título.");
        }

//        System.out.println("A partir de que ano você deseja ver os episódios?");
//        var ano = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null &&
//                        e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                "Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacaoImdb() > 0.0)
                .collect(Collectors.groupingBy(Episodio:: getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacaoImdb)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics estatisticas = episodios.stream()
                .filter(e -> e.getAvaliacaoImdb() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacaoImdb));
        System.out.println("Média: " + estatisticas.getAverage());
        System.out.println("Melhor episódio: " + estatisticas.getMax());
        System.out.println("Pior espisódio: " + estatisticas.getMin());
    }
}
