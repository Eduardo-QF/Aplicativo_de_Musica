package br.com.streaming.util;

public class Validador {

    // Valida se o nome não está vazio
    public static boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    // Valida email simples
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    // Valida duração positiva
    public static boolean validarDuracao(int duracao) {
        return duracao > 0;
    }

    // Valida se o índice está dentro dos limites da lista
    public static boolean validarIndice(int indice, int tamanhoLista) {
        return indice >= 0 && indice < tamanhoLista;
    }
}