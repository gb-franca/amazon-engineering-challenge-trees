package avl;

import interfaces.ArvoreBalanceada;

/**
 * Implementação de Árvore AVL (Adelson-Velsky e Landis)
 * Estrutura estritamente balanceada pela diferença de altura de subárvores (|FB| <= 1).
 */
public class ArvoreAVL implements ArvoreBalanceada {
    private NoAVL raiz;
    private int totalRotacoes = 0;
    private int rotacoesSimples = 0;
    private int rotacoesDuplas = 0;
    private int quantidadeNos = 0;
    private boolean modoSilencioso = false;

    public ArvoreAVL() {
        this.raiz = null;
    }

    public void setModoSilencioso(boolean silencioso) {
        this.modoSilencioso = silencioso;
    }

    private int getAlturaNo(NoAVL no) {
        if (no == null) return 0;
        return no.altura;
    }

    public int getFatorBalanceamento(NoAVL no) {
        if (no == null) return 0;
        return getAlturaNo(no.esquerdo) - getAlturaNo(no.direito);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        this.totalRotacoes++;
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;

        x.direito = y;
        y.esquerdo = T2;

        y.altura = Math.max(getAlturaNo(y.esquerdo), getAlturaNo(y.direito)) + 1;
        x.altura = Math.max(getAlturaNo(x.esquerdo), getAlturaNo(x.direito)) + 1;
        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        this.totalRotacoes++;
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;

        y.esquerdo = x;
        x.direito = T2;

        x.altura = Math.max(getAlturaNo(x.esquerdo), getAlturaNo(x.direito)) + 1;
        y.altura = Math.max(getAlturaNo(y.esquerdo), getAlturaNo(y.direito)) + 1;
        return y;
    }

    private NoAVL rotacaoEsquerdaDireita(NoAVL z) {
        this.rotacoesDuplas++;
        z.esquerdo = rotacaoEsquerda(z.esquerdo);
        return rotacaoDireita(z);
    }

    private NoAVL rotacaoDireitaEsquerda(NoAVL z) {
        this.rotacoesDuplas++;
        z.direito = rotacaoDireita(z.direito);
        return rotacaoEsquerda(z);
    }

    @Override
    public boolean buscar(int valor) {
        return buscaRecursiva(this.raiz, valor);
    }

    private boolean buscaRecursiva(NoAVL no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        if (valor < no.valor) return buscaRecursiva(no.esquerdo, valor);
        return buscaRecursiva(no.direito, valor);
    }

    @Override
    public void inserir(int valor) {
        int qtdAntes = this.quantidadeNos;
        this.raiz = inserirRecursivo(this.raiz, valor);
        if (this.quantidadeNos > qtdAntes && !modoSilencioso) {
            // Sucesso na inserção
        }
    }

    private NoAVL inserirRecursivo(NoAVL no, int valor) {
        if (no == null) {
            this.quantidadeNos++;
            return new NoAVL(valor);
        }

        if (valor < no.valor) {
            no.esquerdo = inserirRecursivo(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = inserirRecursivo(no.direito, valor);
        } else {
            if (!modoSilencioso) {
                System.out.println("Aviso: O identificador " + valor + " já existe. Inserção ignorada!");
            }
            return no;
        }

        no.altura = 1 + Math.max(getAlturaNo(no.esquerdo), getAlturaNo(no.direito));
        int fator = getFatorBalanceamento(no);

        // Caso 1: Rotação Simples à Direita (LL)
        if (fator > 1 && valor < no.esquerdo.valor) {
            rotacoesSimples++;
            return rotacaoDireita(no);
        }

        // Caso 2: Rotação Simples à Esquerda (RR)
        if (fator < -1 && valor > no.direito.valor) {
            rotacoesSimples++;
            return rotacaoEsquerda(no);
        }

        // Caso 3: Rotação Dupla Esquerda-Direita (LR)
        if (fator > 1 && valor > no.esquerdo.valor) {
            return rotacaoEsquerdaDireita(no);
        }

        // Caso 4: Rotação Dupla Direita-Esquerda (RL)
        if (fator < -1 && valor < no.direito.valor) {
            return rotacaoDireitaEsquerda(no);
        }

        return no;
    }

    @Override
    public void remover(int valor) {
        int qtdAntes = this.quantidadeNos;
        this.raiz = removerRecursivo(this.raiz, valor);
        if (this.quantidadeNos < qtdAntes && !modoSilencioso) {
            // Sucesso na remoção
        }
    }

    private NoAVL removerRecursivo(NoAVL no, int valor) {
        if (no == null) {
            if (!modoSilencioso) {
                System.out.println("Aviso: O identificador " + valor + " não existe. Remoção ignorada.");
            }
            return null;
        }

        if (valor < no.valor) {
            no.esquerdo = removerRecursivo(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = removerRecursivo(no.direito, valor);
        } else {
            // Nó localizado para remoção
            this.quantidadeNos--;

            // Caso com 0 ou 1 filho
            if (no.esquerdo == null) {
                return no.direito;
            } else if (no.direito == null) {
                return no.esquerdo;
            } else {
                // Caso com 2 filhos: sucessor em ordem (menor da subárvore direita)
                NoAVL sucessor = noMinimo(no.direito);
                no.valor = sucessor.valor;
                // Reverte decremento provisório pois removerRecursivo fará o decremento real
                this.quantidadeNos++;
                no.direito = removerRecursivo(no.direito, sucessor.valor);
            }
        }

        if (no == null) return null;

        no.altura = 1 + Math.max(getAlturaNo(no.esquerdo), getAlturaNo(no.direito));
        int fator = getFatorBalanceamento(no);

        // Desbalanceamento Esquerda
        if (fator > 1) {
            if (getFatorBalanceamento(no.esquerdo) >= 0) {
                rotacoesSimples++;
                return rotacaoDireita(no);
            } else {
                return rotacaoEsquerdaDireita(no);
            }
        }

        // Desbalanceamento Direita
        if (fator < -1) {
            if (getFatorBalanceamento(no.direito) <= 0) {
                rotacoesSimples++;
                return rotacaoEsquerda(no);
            } else {
                return rotacaoDireitaEsquerda(no);
            }
        }

        return no;
    }

    private NoAVL noMinimo(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    @Override
    public void imprimirEmOrdem() {
        System.out.print("Elementos (Ordem Crescente): ");
        if (estaVazia()) {
            System.out.println("(Árvore vazia)");
            return;
        }
        imprimirEmOrdemRecursiva(this.raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRecursiva(NoAVL no) {
        if (no != null) {
            imprimirEmOrdemRecursiva(no.esquerdo);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRecursiva(no.direito);
        }
    }

    @Override
    public void imprimirEstrutura() {
        if (this.raiz == null) {
            System.out.println("\n(Árvore vazia)");
            return;
        }
        System.out.println("\n=== Estrutura da Árvore AVL ===");
        System.out.println("[Raiz] " + this.raiz.valor + " (h=" + this.raiz.altura + ", fb=" + getFatorBalanceamento(this.raiz) + ")");

        boolean temEsq = (this.raiz.esquerdo != null);
        boolean temDir = (this.raiz.direito != null);

        if (temEsq && temDir) {
            imprimirSubArvore(this.raiz.esquerdo, "", true, "E");
            imprimirSubArvore(this.raiz.direito, "", false, "D");
        } else if (temEsq) {
            imprimirSubArvore(this.raiz.esquerdo, "", false, "E");
        } else if (temDir) {
            imprimirSubArvore(this.raiz.direito, "", false, "D");
        }
        System.out.println();
    }

    private void imprimirSubArvore(NoAVL no, String prefixo, boolean temIrmao, String tipoFilho) {
        if (no == null) return;

        System.out.print(prefixo);
        System.out.print(temIrmao ? "├── " : "└── ");
        System.out.println("(" + tipoFilho + ") " + no.valor + " (h=" + no.altura + ", fb=" + getFatorBalanceamento(no) + ")");

        String novoPrefixo = prefixo + (temIrmao ? "│   " : "    ");

        boolean temEsq = (no.esquerdo != null);
        boolean temDir = (no.direito != null);

        if (temEsq && temDir) {
            imprimirSubArvore(no.esquerdo, novoPrefixo, true, "E");
            imprimirSubArvore(no.direito, novoPrefixo, false, "D");
        } else if (temEsq) {
            imprimirSubArvore(no.esquerdo, novoPrefixo, false, "E");
        } else if (temDir) {
            imprimirSubArvore(no.direito, novoPrefixo, false, "D");
        }
    }

    @Override
    public int getAltura() {
        return getAlturaNo(this.raiz);
    }

    @Override
    public int getTotalRotacoes() {
        return this.totalRotacoes;
    }

    public int getRotacoesSimples() {
        return this.rotacoesSimples;
    }

    public int getRotacoesDuplas() {
        return this.rotacoesDuplas;
    }

    @Override
    public int getQuantidadeNos() {
        return this.quantidadeNos;
    }

    @Override
    public boolean estaVazia() {
        return this.raiz == null;
    }

    @Override
    public void resetarEstatisticas() {
        this.totalRotacoes = 0;
        this.rotacoesSimples = 0;
        this.rotacoesDuplas = 0;
    }

    @Override
    public boolean validarPropriedades() {
        return validarAVLRecursivo(this.raiz) != -1;
    }

    private int validarAVLRecursivo(NoAVL no) {
        if (no == null) return 0;

        int hEsq = validarAVLRecursivo(no.esquerdo);
        int hDir = validarAVLRecursivo(no.direito);

        if (hEsq == -1 || hDir == -1) return -1;

        int fb = hEsq - hDir;
        if (fb < -1 || fb > 1) {
            return -1; // Violação da propriedade AVL
        }

        // Validação da propriedade de BST
        if (no.esquerdo != null && no.esquerdo.valor >= no.valor) return -1;
        if (no.direito != null && no.direito.valor <= no.valor) return -1;

        return 1 + Math.max(hEsq, hDir);
    }

    public NoAVL getRaiz() {
        return this.raiz;
    }
}