package br.com.streaming.util;

public class FormatarTempo {

    // Converte segundos para formato MM:SS
    public static String formatarSegundos(int segundos) {
        int minutos = segundos / 60;
        int segsRestantes = segundos % 60;
        return String.format("%02d:%02d", minutos, segsRestantes);
    }

    // Converte segundos para formato HH:MM:SS
    public static String formatarCompleto(int segundos) {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segsRestantes = segundos % 60;

        if (horas > 0) {
            return String.format("%02d:%02d:%02d", horas, minutos, segsRestantes);
        }
        return String.format("%02d:%02d", minutos, segsRestantes);
    }

    // Exibe o tempo de forma legível
    public static String tempoLegivel(int segundos) {
        if (segundos < 60) {
            return segundos + " segundos";
        } else if (segundos < 3600) {
            return (segundos / 60) + " minutos e " + (segundos % 60) + " segundos";
        } else {
            return (segundos / 3600) + " horas, " + ((segundos % 3600) / 60) + " minutos";
        }
    }
}