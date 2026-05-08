import java.util.ArrayList;

public class PlaylistAutomatica extends Playlist {

    private String criterio;

    public PlaylistAutomatica() {
        super();
        this.criterio = "top";
    }

    public PlaylistAutomatica(String nome, String criterio) {
        super(nome);
        setCriterio(criterio);
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("Critério não pode ser nulo ou vazio.");
        }

        String c = criterio.toLowerCase();

        if (!c.equals("top") && !c.equals("recomendadas") && !c.equals("recentes")) {
            throw new IllegalArgumentException("Critério inválido. Use: top, recomendadas ou recentes.");
        }

        this.criterio = c;
    }

    @Override
    public void reproduzir() {
        System.out.println("🤖 Playlist Automática: " + nome);
        System.out.println("📊 Critério: " + criterio);
        super.reproduzir();
    }

    public void atualizar(ArrayList<Musica> todasMusicas) {
        musicas.clear();

        if (todasMusicas == null || todasMusicas.isEmpty()) {
            return;
        }

        if (criterio.equals("top")) {
            todasMusicas.sort((a, b) -> Integer.compare(b.getNumeroReproducoes(), a.getNumeroReproducoes()));
            for (int i = 0; i < Math.min(10, todasMusicas.size()); i++) {
                musicas.add(todasMusicas.get(i));
            }

        } else if (criterio.equals("recomendadas")) {
            for (Musica m : todasMusicas) {
                if (m.isRecomendada()) {
                    musicas.add(m);
                }
            }

        } else if (criterio.equals("recentes")) {
            todasMusicas.sort((a, b) -> b.getDataAdicao().compareTo(a.getDataAdicao()));
            for (int i = 0; i < Math.min(10, todasMusicas.size()); i++) {
                musicas.add(todasMusicas.get(i));
            }
        }
    }
}