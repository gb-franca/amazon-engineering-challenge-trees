# Amazon Engineering Challenge - Árvores Balanceadas (AVL e Rubro-Negra)
> **Trabalho da Unidade II — Estruturas de Dados Avançadas**  
> **Cenário:** Sistema de Logística de Rastreamento de Encomendas — Black Friday Amazon  
> **Papel:** Tech Lead de Engenharia de Software  

---

## 1. Identificação do Autor
* **Autor:** Gabriel Soares Ferreira de França
* **Linguagem:** Java (JDK 17+)
* **Repositório:** [https://github.com/gb-franca/amazon-engineering-challenge-trees](https://github.com/gb-franca/amazon-engineering-challenge-trees)

---

## 2. Contexto do Problema (Black Friday na Amazon)
Durante a Black Friday, o fluxo de encomendas aumenta exponencialmente. O sistema de logística central da Amazon necessita registrar, localizar e despachar (remover) centenas de milhares de identificadores de pacotes por segundo.

Uma Árvore Binária de Busca (BST) convencional degrada para complexidade linear $\mathcal{O}(n)$ quando as encomendas chegam em ordens ordenadas ou semi-ordenadas (por exemplo, IDs gerados sequencialmente por relógios ou lotes), transformando a árvore em uma lista encadeada. Para garantir previsibilidade e alta performance $\mathcal{O}(\log n)$ sob qualquer padrão de tráfego, foi desenvolvido e analisado este protótipo comparando duas arquiteturas clássicas de árvores balanceadas:
1. **Árvore AVL (Adelson-Velsky e Landis)**
2. **Árvore Rubro-Negra (Red-Black Tree - CLRS)**

---

## 3. Estrutura do Projeto

```
amazon-engineering-challenge-trees/
├── avl/
│   ├── ArvoreAVL.java         # Implementação canônica da Árvore AVL
│   └── NoAVL.java             # Nó da AVL com altura e fator de balanceamento
├── rubronegra/
│   ├── ArvoreRubroNegra.java  # Implementação da Rubro-Negra com sentinela T_NIL
│   └── NoRubroNegra.java      # Nó Rubro-Negro com cor (VERMELHO/PRETO) e ponteiro pai
├── interfaces/
│   └── ArvoreBalanceada.java  # Interface unificada para as operações do sistema
├── utils/
│   └── TestesComparativos.java# Bateria automatizada de benchmark e casos de teste
├── src/
│   └── Main.java              # Ponto de entrada com menu CLI e modos interativos
├── bin/                       # Bytecodes compilados (.class)
├── amazon-challenge-trees.jar # Pacote executável portátil (JAR)
└── README.md                  # Documentação técnica completa
```

---

## 4. Funcionalidades e Requisitos Atendidos

| Operação Requerida | Descrição e Tratamento Adotado |
| :--- | :--- |
| **Inserção** | Insere elemento e reequilibra a árvore preservando as propriedades. Valores duplicados são identificados em $\mathcal{O}(\log n)$, geram aviso e são ignorados sem alocação desnecessária. |
| **Remoção** | Remove nós folha, nós com 1 filho e nós com 2 filhos (substituição por sucessor em ordem). Se o nó não existir, emite aviso e mantém a árvore íntegra. |
| **Busca** | Retorna `true` se a encomenda existe ou `false` se não existir, em tempo estritamente $\mathcal{O}(\log n)$. |
| **Percurso em Ordem** | Apresenta os identificadores em ordem estritamente crescente via caminhamento in-order. |
| **Visualização Hierárquica** | Exibe a estrutura da árvore em ASCII formatado (`├──` e `└──`). Na Rubro-Negra, exibe as cores dos nós (`VERMELHO` e `PRETO`) com suporte a realce ANSI. Na AVL, exibe a altura ($h$) e o fator de balanceamento ($fb$). |
| **Casos de Borda** | Árvores vazias, árvores com 1 único nó, remoção da raiz e sequências monótonas são testadas e validadas sem falhas. |
| **Validação Matemática** | Métodos `validarPropriedades()` verificam programaticamente se os invariantes da AVL ($|FB| \le 1$) e da Rubro-Negra (regras de nós vermelhos e altura negra idêntica) foram preservados. |

---

## 5. Como Compilar e Executar

### Pré-requisitos
* Java Development Kit (JDK 11 ou superior instalado).

### Opção 1: Executando o JAR Pronto (Recomendado)
```bash
java -jar amazon-challenge-trees.jar
```

### Opção 2: Compilando a partir do Código-Fonte
```bash
# 1. Compilar todas as classes para a pasta bin
javac -d bin src/Main.java avl/*.java rubronegra/*.java interfaces/*.java utils/*.java

# 2. Executar o programa
java -cp bin Main
```

---

## 6. Resultados Empíricos Obtidos nos Testes (Benchmark)

Os testes foram executados com $N = 1000$ operações em três perfis de carga com os mesmos dados em ambas as árvores:

| Cenário de Teste | Métrica | Árvore AVL | Árvore Rubro-Negra |
| :--- | :--- | :--- | :--- |
| **1. Ordem Crescente (1 a 1000)** *(Pior caso para BST simples)* | **Altura Final**<br>**Total de Rotações**<br>**Total de Recolorações**<br>**Tempo Inserção (ms)**<br>**Tempo Busca (ms)**<br>**Tempo Remoção (ms)** | **10**<br>1238<br>*N/A*<br>3.61 ms<br>1.16 ms<br>1.07 ms | **15**<br>1232<br>5907<br>**2.12 ms**<br>**0.78 ms**<br>**0.45 ms** |
| **2. Ordem Decrescente (1000 a 1)** | **Altura Final**<br>**Total de Rotações**<br>**Total de Recolorações**<br>**Tempo Inserção (ms)**<br>**Tempo Remoção (ms)** | **10**<br>1238<br>*N/A*<br>2.40 ms<br>0.21 ms | **15**<br>1232<br>5907<br>**0.44 ms**<br>**0.14 ms** |
| **3. Aleatório / Uniforme (Black Friday Real)** | **Altura Final**<br>**Total de Rotações**<br>**Total de Recolorações**<br>**Tempo Inserção (ms)**<br>**Tempo Busca (ms)**<br>**Tempo Remoção (ms)** | **10**<br>1211<br>*N/A*<br>0.52 ms<br>0.32 ms<br>0.35 ms | **13**<br>1355<br>5734<br>**0.29 ms**<br>0.33 ms<br>**0.22 ms** |

> **Invariantes e Propriedades:** Em 100% dos cenários e após todas as inserções e remoções de nós com 0, 1 e 2 filhos, ambas as árvores retornaram `validarPropriedades() == true`.

---

## 7. Análise Técnica Comparativa

### 1. Estratégia de Balanceamento
* **Árvore AVL:** Rigidamente balanceada. O fator de balanceamento $FB(u) = h_{esq} - h_{dir}$ deve pertencer estritamente a $\{-1, 0, 1\}$. Sua altura máxima é limitada a $\approx 1.44 \log_2(n)$.
* **Árvore Rubro-Negra:** Balanceamento relaxado via coloração. Garante que nenhum caminho seja mais que duas vezes mais longo que qualquer outro caminho, limitando a altura a no máximo $2 \log_2(n+1)$.

### 2. Custo de Rotações vs. Recolorações
* A Árvore Rubro-Negra prioriza **recoloração** dos nós (operação de atribuição $O(1)$ em memória cache) em vez de rotações pesadas de ponteiros. Na inserção, a Rubro-Negra realiza **no máximo 2 rotações**, enquanto na remoção realiza **no máximo 3 rotações**.
* A Árvore AVL pode necessitar propagar rotações por toda a cadeia de ancestrais até a raiz durante a remoção ($O(\log n)$ rotações).

### 3. Altura e Desempenho de Busca
* Como a AVL possui menor altura (10 contra 13-15 da Rubro-Negra para 1.000 nós), a AVL teoricamente realiza menos comparações em cenários onde a carga é quase exclusivamente de leitura/busca estática.

---

## 8. Recomendação Técnica para a Amazon Logistics

> **Veredito do Tech Lead:** **Árvore Rubro-Negra (Red-Black Tree)** é a estrutura recomendada para o Core de Logística da Black Friday.

**Justificativa Técnica:**
1. **Perfil de Carga Misto e Dinâmico:** Em logística de tempo real durante a Black Friday, o número de inserções de novos pedidos e remoções por despacho para transporte é contínuo e volumoso (alta taxa de mutação).
2. **Menor Sobrecarga de Mutação:** O custo de ajuste da Rubro-Negra baseia-se primordialmente em recolorações baratas de nós sem mover ponteiros de filhos na memória, resultando em menor latência em inserções e remoções.
3. **Consistência em Bibliotecas de Produção:** Pelo mesmo motivo, a Rubro-Negra é o padrão adotado internamente em estruturas do ecossistema de alto desempenho (como `java.util.TreeMap` / `TreeSet` no Java, `std::map` em C++ e no agendador CFS do kernel Linux).

