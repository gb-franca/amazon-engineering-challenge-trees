package avl;

public class NoAVL {
    public int valor;
    public int altura;
    public NoAVL esquerdo;
    public NoAVL direito;

    public NoAVL(int valor){
        this.valor = valor;
        this.altura = 1;
        this.esquerdo = null;
        this.direito = null;
    }
}
    