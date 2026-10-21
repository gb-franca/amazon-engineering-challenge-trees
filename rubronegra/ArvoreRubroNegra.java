package rubronegra;

import interfaces.ArvoreBalanceada;

/**
 * Implementação de Árvore Rubro-Negra (Red-Black Tree)
 * Baseada no algoritmo clássico de Cormen, Leiserson, Rivest e Stein (CLRS).
 * Balanceamento aproximado garantindo que o caminho mais longo seja no máximo o dobro do caminho mais curto.
 */
public class ArvoreRubroNegra implements ArvoreBalanceada {
    private final NoRubroNegra T_NIL;
    private NoRubroNegra raiz;
    private int totalRotacoes = 0;
    private int totalRecoloracoes = 0;
    private int quantidadeNos = 0;
    private boolean modoSilencioso = false;

    // Cores ANSI para visualização elegante no terminal
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31;1m";
    private static final String ANSI_WHITE = "\u001B[37;1m";

    public ArvoreRubroNegra() {
        T_NIL = new NoRubroNegra(0);
        T_NIL.cor = NoRubroNegra.PRETO;
        T_NIL.esquerdo = null;
        T_NIL.direito = null;
        T_NIL.pai = null;
        this.raiz = T_NIL;
    }

    public void setModoSilencioso(boolean silencioso) {
        this.modoSilencioso = silencioso;
    }

    // Rotação simples à esquerda
    private void rotacaoEsquerda(NoRubroNegra x) {
        this.totalRotacoes++;
        NoRubroNegra y = x.direito;
        x.direito = y.esquerdo;

        if (y.esquerdo != T_NIL) {
            y.esquerdo.pai = x;
        }

        y.pai = x.pai;

        if (x.pai == T_NIL || x.pai == null) {
            this.raiz = y;
        } else if (x == x.pai.esquerdo) {
            x.pai.esquerdo = y;
        } else {
            x.pai.direito = y;
        }

        y.esquerdo = x;
        x.pai = y;
    }

    // Rotação simples à direita
    private void rotacaoDireita(NoRubroNegra y) {
        this.totalRotacoes++;
        NoRubroNegra x = y.esquerdo;
        y.esquerdo = x.direito;

        if (x.direito != T_NIL) {
            x.direito.pai = y;
        }

        x.pai = y.pai;

        if (y.pai == T_NIL || y.pai == null) {
            this.raiz = x;
        } else if (y == y.pai.direito) {
            y.pai.direito = x;
        } else {
            y.pai.esquerdo = x;
        }

        x.direito = y;
        y.pai = x;
    }

    @Override
    public boolean buscar(int valor) {
        return buscarNo(this.raiz, valor) != T_NIL;
    }

    private NoRubroNegra buscarNo(NoRubroNegra no, int valor) {
        while (no != T_NIL && no != null && valor != no.valor) {
            if (valor < no.valor) {
                no = no.esquerdo;
            } else {
                no = no.direito;
            }
        }
        return (no == null) ? T_NIL : no;
    }

    @Override
    public void inserir(int valor) {
        NoRubroNegra y = T_NIL;
        NoRubroNegra x = this.raiz;

        // Otimização: verificação de duplicidade diretamente durante a descida (elimina busca prévia redundante)
        while (x != T_NIL && x != null) {
            if (valor == x.valor) {
                if (!modoSilencioso) {
                    System.out.println("Aviso: O identificador " + valor + " já existe. Inserção ignorada!");
                }
                return;
            }
            y = x;
            if (valor < x.valor) {
                x = x.esquerdo;
            } else {
                x = x.direito;
            }
        }

        NoRubroNegra z = new NoRubroNegra(valor);
        z.esquerdo = T_NIL;
        z.direito = T_NIL;
        z.cor = NoRubroNegra.VERMELHO;
        z.pai = y;

        if (y == T_NIL || y == null) {
            this.raiz = z;
        } else if (z.valor < y.valor) {
            y.esquerdo = z;
        } else {
            y.direito = z;
        }

        this.quantidadeNos++;
        inserirFixup(z);
    }

    private void inserirFixup(NoRubroNegra z) {
        while (z.pai != null && z.pai.cor == NoRubroNegra.VERMELHO) {
            if (z.pai.pai == null) break;

            if (z.pai == z.pai.pai.esquerdo) {
                NoRubroNegra tio = z.pai.pai.direito;

                // Caso 1: Tio é vermelho -> recoloração simples sem rotação
                if (tio != null && tio.cor == NoRubroNegra.VERMELHO) {
                    z.pai.cor = NoRubroNegra.PRETO;
                    tio.cor = NoRubroNegra.PRETO;
                    z.pai.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 3;
                    z = z.pai.pai;
                } else {
                    // Caso 2: Tio é preto e z é filho direito -> rotação à esquerda
                    if (z == z.pai.direito) {
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }
                    // Caso 3: Tio é preto e z é filho esquerdo -> rotação à direita no avô
                    z.pai.cor = NoRubroNegra.PRETO;
                    z.pai.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 2;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                // Caso simétrico: pai é filho direito do avô
                NoRubroNegra tio = z.pai.pai.esquerdo;

                // Caso 1: Tio é vermelho -> recoloração simples sem rotação
                if (tio != null && tio.cor == NoRubroNegra.VERMELHO) {
                    z.pai.cor = NoRubroNegra.PRETO;
                    tio.cor = NoRubroNegra.PRETO;
                    z.pai.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 3;
                    z = z.pai.pai;
                } else {
                    // Caso 2: Tio é preto e z é filho esquerdo -> rotação à direita
                    if (z == z.pai.esquerdo) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    // Caso 3: Tio é preto e z é filho direito -> rotação à esquerda no avô
                    z.pai.cor = NoRubroNegra.PRETO;
                    z.pai.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 2;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }

        if (this.raiz.cor != NoRubroNegra.PRETO) {
            this.raiz.cor = NoRubroNegra.PRETO;
            totalRecoloracoes++;
        }
    }

    private void transplante(NoRubroNegra u, NoRubroNegra v) {
        if (u.pai == T_NIL || u.pai == null) {
            this.raiz = v;
        } else if (u == u.pai.esquerdo) {
            u.pai.esquerdo = v;
        } else {
            u.pai.direito = v;
        }
        if (v != null) {
            v.pai = u.pai;
        }
    }

    private NoRubroNegra minimo(NoRubroNegra no) {
        while (no.esquerdo != T_NIL && no.esquerdo != null) {
            no = no.esquerdo;
        }
        return no;
    }

    @Override
    public void remover(int valor) {
        NoRubroNegra z = buscarNo(this.raiz, valor);
        if (z == T_NIL || z == null) {
            if (!modoSilencioso) {
                System.out.println("Aviso: O identificador " + valor + " não existe. Remoção ignorada.");
            }
            return;
        }

        NoRubroNegra y = z;
        boolean corOriginalDeY = y.cor;
        NoRubroNegra x;

        if (z.esquerdo == T_NIL) {
            x = z.direito;
            transplante(z, z.direito);
        } else if (z.direito == T_NIL) {
            x = z.esquerdo;
            transplante(z, z.esquerdo);
        } else {
            y = minimo(z.direito);
            corOriginalDeY = y.cor;
            x = y.direito;

            if (y.pai == z) {
                if (x != null) x.pai = y;
            } else {
                transplante(y, y.direito);
                y.direito = z.direito;
                y.direito.pai = y;
            }

            transplante(z, y);
            y.esquerdo = z.esquerdo;
            y.esquerdo.pai = y;
            y.cor = z.cor;
        }

        this.quantidadeNos--;

        if (corOriginalDeY == NoRubroNegra.PRETO) {
            removerFixup(x);
        }
        T_NIL.pai = null;
    }

    private void removerFixup(NoRubroNegra x) {
        while (x != this.raiz && x != null && x.cor == NoRubroNegra.PRETO) {
            if (x.pai == null || x.pai == T_NIL) break;

            if (x == x.pai.esquerdo) {
                NoRubroNegra irmao = x.pai.direito;

                // Caso 1: Irmão é vermelho
                if (irmao.cor == NoRubroNegra.VERMELHO) {
                    irmao.cor = NoRubroNegra.PRETO;
                    x.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 2;
                    rotacaoEsquerda(x.pai);
                    irmao = x.pai.direito;
                }

                // Caso 2: Irmão é preto e ambos os filhos são pretos
                if (irmao.esquerdo.cor == NoRubroNegra.PRETO && irmao.direito.cor == NoRubroNegra.PRETO) {
                    irmao.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes++;
                    x = x.pai;
                } else {
                    // Caso 3: Irmão é preto, filho esquerdo é vermelho e direito é preto
                    if (irmao.direito.cor == NoRubroNegra.PRETO) {
                        irmao.esquerdo.cor = NoRubroNegra.PRETO;
                        irmao.cor = NoRubroNegra.VERMELHO;
                        totalRecoloracoes += 2;
                        rotacaoDireita(irmao);
                        irmao = x.pai.direito;
                    }

                    // Caso 4: Irmão é preto e filho direito é vermelho
                    irmao.cor = x.pai.cor;
                    x.pai.cor = NoRubroNegra.PRETO;
                    irmao.direito.cor = NoRubroNegra.PRETO;
                    totalRecoloracoes += 3;
                    rotacaoEsquerda(x.pai);
                    x = this.raiz;
                }
            } else {
                // Caso simétrico: x é filho direito
                NoRubroNegra irmao = x.pai.esquerdo;

                // Caso 1: Irmão é vermelho
                if (irmao.cor == NoRubroNegra.VERMELHO) {
                    irmao.cor = NoRubroNegra.PRETO;
                    x.pai.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes += 2;
                    rotacaoDireita(x.pai);
                    irmao = x.pai.esquerdo;
                }

                // Caso 2: Irmão é preto e ambos os filhos são pretos
                if (irmao.direito.cor == NoRubroNegra.PRETO && irmao.esquerdo.cor == NoRubroNegra.PRETO) {
                    irmao.cor = NoRubroNegra.VERMELHO;
                    totalRecoloracoes++;
                    x = x.pai;
                } else {
                    // Caso 3: Irmão é preto, filho direito é vermelho e esquerdo é preto
                    if (irmao.esquerdo.cor == NoRubroNegra.PRETO) {
                        irmao.direito.cor = NoRubroNegra.PRETO;
                        irmao.cor = NoRubroNegra.VERMELHO;
                        totalRecoloracoes += 2;
                        rotacaoEsquerda(irmao);
                        irmao = x.pai.esquerdo;
                    }

                    // Caso 4: Irmão é preto e filho esquerdo é vermelho
                    irmao.cor = x.pai.cor;
                    x.pai.cor = NoRubroNegra.PRETO;
                    irmao.esquerdo.cor = NoRubroNegra.PRETO;
                    totalRecoloracoes += 3;
                    rotacaoDireita(x.pai);
                    x = this.raiz;
                }
            }
        }
        if (x != null && x != T_NIL) {
            x.cor = NoRubroNegra.PRETO;
        }
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

    private void imprimirEmOrdemRecursiva(NoRubroNegra no) {
        if (no != T_NIL && no != null) {
            imprimirEmOrdemRecursiva(no.esquerdo);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRecursiva(no.direito);
        }
    }

    @Override
    public void imprimirEstrutura() {
        if (this.raiz == T_NIL || this.raiz == null) {
            System.out.println("\n(Árvore vazia)");
            return;
        }
        System.out.println("\n=== Estrutura da Árvore Rubro-Negra ===");
        String corRaiz = (this.raiz.cor == NoRubroNegra.VERMELHO) ? "VERMELHO" : "PRETO";
        String corFormatada = formatarCor(corRaiz);
        System.out.println("[Raiz] " + this.raiz.valor + " (" + corFormatada + ")");

        boolean temEsq = (this.raiz.esquerdo != T_NIL && this.raiz.esquerdo != null);
        boolean temDir = (this.raiz.direito != T_NIL && this.raiz.direito != null);

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

    private void imprimirSubArvore(NoRubroNegra no, String prefixo, boolean temIrmao, String tipoFilho) {
        if (no == T_NIL || no == null) return;

        String corStr = (no.cor == NoRubroNegra.VERMELHO) ? "VERMELHO" : "PRETO";
        String corFormatada = formatarCor(corStr);

        System.out.print(prefixo);
        System.out.print(temIrmao ? "├── " : "└── ");
        System.out.println("(" + tipoFilho + ") " + no.valor + " (" + corFormatada + ")");

        String novoPrefixo = prefixo + (temIrmao ? "│   " : "    ");

        boolean temEsq = (no.esquerdo != T_NIL && no.esquerdo != null);
        boolean temDir = (no.direito != T_NIL && no.direito != null);

        if (temEsq && temDir) {
            imprimirSubArvore(no.esquerdo, novoPrefixo, true, "E");
            imprimirSubArvore(no.direito, novoPrefixo, false, "D");
        } else if (temEsq) {
            imprimirSubArvore(no.esquerdo, novoPrefixo, false, "E");
        } else if (temDir) {
            imprimirSubArvore(no.direito, novoPrefixo, false, "D");
        }
    }

    private String formatarCor(String corStr) {
        if ("VERMELHO".equals(corStr)) {
            return ANSI_RED + "VERMELHO" + ANSI_RESET;
        }
        return ANSI_WHITE + "PRETO" + ANSI_RESET;
    }

    @Override
    public int getAltura() {
        return calcularAltura(this.raiz);
    }

    private int calcularAltura(NoRubroNegra no) {
        if (no == T_NIL || no == null) return 0;
        return 1 + Math.max(calcularAltura(no.esquerdo), calcularAltura(no.direito));
    }

    public int getAlturaNegra() {
        int ah = 0;
        NoRubroNegra curr = this.raiz;
        while (curr != T_NIL && curr != null) {
            if (curr.cor == NoRubroNegra.PRETO) ah++;
            curr = curr.esquerdo;
        }
        return ah;
    }

    @Override
    public int getTotalRotacoes() {
        return this.totalRotacoes;
    }

    public int getTotalRecoloracoes() {
        return this.totalRecoloracoes;
    }

    @Override
    public int getQuantidadeNos() {
        return this.quantidadeNos;
    }

    @Override
    public boolean estaVazia() {
        return this.raiz == T_NIL || this.raiz == null;
    }

    @Override
    public void resetarEstatisticas() {
        this.totalRotacoes = 0;
        this.totalRecoloracoes = 0;
    }

    @Override
    public boolean validarPropriedades() {
        if (this.raiz == T_NIL || this.raiz == null) return true;

        // Propriedade 1: Raiz deve ser PRETA
        if (this.raiz.cor != NoRubroNegra.PRETO) return false;

        // Propriedades 2 e 3: Sem nós vermelhos consecutivos e mesma altura negra em todos os caminhos
        return checarPropriedadesRN(this.raiz) != -1;
    }

    private int checarPropriedadesRN(NoRubroNegra no) {
        if (no == T_NIL || no == null) return 1;

        // Se um nó é VERMELHO, ambos os filhos devem ser PRETOS
        if (no.cor == NoRubroNegra.VERMELHO) {
            if (no.esquerdo.cor == NoRubroNegra.VERMELHO || no.direito.cor == NoRubroNegra.VERMELHO) {
                return -1; // Violação: nós vermelhos adjacentes
            }
        }

        // Validação da propriedade de BST
        if (no.esquerdo != T_NIL && no.esquerdo.valor >= no.valor) return -1;
        if (no.direito != T_NIL && no.direito.valor <= no.valor) return -1;

        int anEsq = checarPropriedadesRN(no.esquerdo);
        int anDir = checarPropriedadesRN(no.direito);

        if (anEsq == -1 || anDir == -1 || anEsq != anDir) {
            return -1; // Violação de altura negra inconsistente
        }

        return anEsq + (no.cor == NoRubroNegra.PRETO ? 1 : 0);
    }

    public NoRubroNegra getRaiz() {
        return this.raiz;
    }
}
