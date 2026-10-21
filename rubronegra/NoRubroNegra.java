package rubronegra;

public class NoRubroNegra {
    public static final boolean VERMELHO = true;
    public static final boolean PRETO = false;

    public int valor;
    public boolean cor;
    public NoRubroNegra esquerdo;
    public NoRubroNegra direito;
    public NoRubroNegra pai;

    public NoRubroNegra(int valor) {
        this.valor = valor;
        this.cor = VERMELHO;
        this.esquerdo = null;
        this.direito = null;
        this.pai = null;
    }
}