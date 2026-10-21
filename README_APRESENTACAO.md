# Documentação e Prompt para Apresentação Técnica em HTML / PDF

> **Projeto:** Amazon Engineering Challenge — Árvores Balanceadas (AVL e Rubro-Negra)  
> **Autor:** Gabriel Soares Ferreira de França  
> **Finalidade:** Prompt pronto e estruturado para gerar uma aplicação de slides em HTML/CSS/JavaScript pura, responsiva e pronta para exportação direta em PDF via navegador (`Ctrl + P`).

---

## 1. Instruções Rápidas de Uso

1. Copie o bloco de prompt da **Seção 2** abaixo.
2. Cole em uma IA geradora de código (como Claude, ChatGPT ou no próprio assistente) para gerar o arquivo `index.html` (ou `slides.html`).
3. Abra o arquivo gerado em qualquer navegador web (Google Chrome, Edge, Firefox).
4. Para gerar o **PDF final da apresentação**:
   * Pressione `Ctrl + P` (ou `Cmd + P` no Mac).
   * Destino: **Salvar como PDF**.
   * Layout: **Paisagem (Landscape)**.
   * Margens: **Nenhuma (None)**.
   * Marcar a opção: **Gráficos de segundo plano (Background graphics)**.
   * Clique em **Salvar**.

---

## 2. Prompt Completo para Gerar a Apresentação Web

```markdown
Você é um desenvolvedor frontend especialista em UI/UX e engenharia de software da Amazon.
Sua tarefa é criar um arquivo único e autossuficiente (`apresentacao.html`) contendo uma Apresentação Técnica em Slides interativa (Slide Deck) para defender a solução do "Amazon Engineering Challenge: Árvores Balanceadas (AVL e Rubro-Negra)".

### Diretrizes de Design e UX:
1. **Identidade Visual Premium (Amazon Logistics):**
   - Paleta: Fundo escuro elegante (#0F1117, #1A1D27), cartões com efeito sutil de profundidade (#232736), detalhes em laranja Amazon (#FF9900) e azul ciano/tech (#00A8E8).
   - Tipografia: Fonte 'Inter' ou 'Segoe UI' do Google Fonts, com hierarquia visual clara, títulos expressivos e badges modernos.
   - Proporção: Slides em proporção 16:9 widescreen, centralizados na viewport.
2. **Navegação e Interatividade:**
   - Navegação por teclado: Teclas de seta direita/esquerda, Barra de Espaço e Page Down/Page Up.
   - Controles visuais no rodapé: Botões Anterior / Próximo, indicador numérico de slide ("Slide 03 / 10") e barra de progresso no topo.
   - Botão flutuante "Exportar para PDF / Imprimir" que dispara `window.print()`.
3. **Suporte Perfeito à Impressão / Exportação PDF:**
   - Regras `@media print` rigorosas:
     - `@page { size: landscape; margin: 0; }`
     - Cada slide deve ter `page-break-after: always;` e `break-after: page;`
     - Esconder botões de navegação, barra de progresso e controles na impressão.
     - Garantir que todos os slides sejam impressos sequencialmente em páginas separadas no PDF.
4. **Sem Dependências Externas Pesadas:**
   - Apenas HTML5, CSS3 moderno (Flexbox/Grid) e Vanilla JavaScript embutidos no mesmo arquivo.

---

### Estrutura Obrigatória dos 10 Slides (Baseada no Edital do Trabalho):

#### Slide 1: Capa & Apresentação da Proposta
- Título: Amazon Engineering Challenge — Logística de Rastreamento Black Friday
- Subtítulo: Avaliação Comparativa de Desempenho entre Árvores AVL e Rubro-Negra
- Cargo: Tech Lead de Engenharia de Software
- Autor: Gabriel Soares Ferreira de França
- Stack: Java 17 | Zero Dependências | 100% Código Nativo

#### Slide 2: O Desafio Técnico (Degeneração de BSTs)
- Contexto da Black Friday: Centenas de milhares de encomendas registradas, consultadas e despachadas por segundo.
- O Problema das BSTs Simples: IDs gerados sequencialmente (lotes, timestamps) causam degeneração para lista encadeada (O(n)), colapsando a latência do sistema.
- A Solução Necessária: Garantia matemática de complexidade O(log n) sob qualquer padrão de tráfego usando estruturas auto-balanceáveis.

#### Slide 3: Arquitetura da Solução Desenvolvida
- Interface Unificada: `ArvoreBalanceada` com inserção, remoção, busca O(log n), percurso em ordem e diagnóstico.
- Tratamento de Exceções e Resiliência:
  * Inserção de IDs duplicados: aviso amigável e descarte sem overhead.
  * Busca e remoção de inexistentes: prevenção de NullPointer com retorno seguro.
  * Casos de borda: árvores vazias e com único nó plenamente suportadas.
- Validação Matemática em Tempo Real: Métodos `validarPropriedades()` verificam os invariantes após cada mutação.

#### Slide 4: Árvore AVL — Teoria & Demonstração de Rotação
- Fundamentação: Balanceamento estrito baseado em altura. Fator de Balanceamento FB = h(esq) - h(dir) ∈ {-1, 0, 1}.
- Rotações: Simples (Esquerda e Direita) e Duplas (Esquerda-Direita e Direita-Esquerda).
- Exemplo Didático "Antes e Depois" (extraído do sistema com IDs 10, 20, 30):
  * Antes da inserção do 30: Raiz 10 -> Filho Dir 20 (pende à direita).
  * Após inserir 30: Rotação simples à esquerda na raiz. Nova raiz 20, filhos 10 e 30 (FB = 0).

#### Slide 5: Árvore Rubro-Negra — Propriedades & Ajuste Didático
- As 5 Regras Invariantes (CLRS):
  1. Todo nó é Vermelho ou Preto.
  2. A raiz é estritamente Preta.
  3. Folhas nulas (sentinelas T_NIL) são Pretas.
  4. Se um nó é Vermelho, ambos os filhos são Pretos (sem vermelhos consecutivos).
  5. Todo caminho da raiz às folhas contém o mesmo número de nós pretos (Altura Negra uniforme).
- Mecanismo: Prioriza recolorações O(1) baratas antes de recorrer a rotações de ponteiros.
- Exemplo Didático: Inserção de 10, 20, 30 demonstrando recoloração e rotação para manter a altura negra.

#### Slide 6: Demonstração do Sistema em Execução (CLI & Visualização)
- Interface Interativa: Menus de controle para testes manuais e dinâmicos.
- Visualização Hierárquica da Estrutura:
  * Exibição visual da árvore em ASCII formatado (`├──` e `└──`).
  * Na Rubro-Negra: Identificação explícita de cores `(VERMELHO)` e `(PRETO)` com destaque cromático.
  * Na AVL: Exibição de altura (h) e fator de balanceamento (fb) nó a nó.
- Percurso Em Ordem: Garantia de listagem ordenada crescente dos pacotes para conferência logística.

#### Slide 7: Metodologia de Testes e Casos de Borda
- Cenários Críticos Validados:
  * Árvores vazias e reinicialização.
  * Inserção e remoção em árvore com nó único.
  * Remoção com 3 configurações de filhos: Nó folha (0 filhos), nó com 1 filho e nó com 2 filhos (substituição por sucessor em ordem).
  * Tentativas de inserção de chaves repetidas e remoção de chaves inexistentes.
  * 100% de integridade confirmada pelos algoritmos de validação formal.

#### Slide 8: Análise Comparativa Empírica (Resultados do Benchmark)
Exibir tabela comparativa rica com os dados reais medidos pelo programa para N = 1.000 nós:

| Métrica Analisada | Cenário 1: Crescente (Pior Caso BST) | Cenário 2: Decrescente | Cenário 3: Aleatório (Black Friday Real) |
| :--- | :--- | :--- | :--- |
| **Altura Final** | AVL: 10 \| RN: 15 | AVL: 10 \| RN: 15 | AVL: 10 \| RN: 13 |
| **Total de Rotações** | AVL: 1238 \| RN: 1232 | AVL: 1238 \| RN: 1232 | AVL: 1211 \| RN: 1355 |
| **Total de Recolorações**| AVL: N/A \| RN: 5907 | AVL: N/A \| RN: 5907 | AVL: N/A \| RN: 5734 |
| **Tempo Inserção (ms)** | AVL: 3.61 ms \| **RN: 2.12 ms** | AVL: 2.40 ms \| **RN: 0.44 ms** | AVL: 0.52 ms \| **RN: 0.29 ms** |
| **Tempo Remoção (ms)**  | AVL: 1.07 ms \| **RN: 0.45 ms** | AVL: 0.21 ms \| **RN: 0.14 ms** | AVL: 0.35 ms \| **RN: 0.22 ms** |
| **Propriedades Válidas**| AVL: SIM \| RN: SIM | AVL: SIM \| RN: SIM | AVL: SIM \| RN: SIM |

#### Slide 9: Recomendação Técnica para a Amazon Logistics
- **Veredito do Tech Lead:** Recomendação da **Árvore Rubro-Negra (Red-Black Tree)** para o Core de Logística.
- Justificativas Fundamentadas:
  1. *Carga com Alta Mutação:* Na Black Friday, pacotes chegam e são despachados ininterruptamente. A Rubro-Negra possui custo de mutação expressivamente menor.
  2. *Recoloração vs Rotação:* A RN prioriza recolorações de bits na memória cache (O(1)), exigindo no máximo 2 rotações na inserção e 3 na remoção, enquanto a AVL pode propagar O(log n) rotações na remoção.
  3. *Padrão Industrial:* Estrutura adotada em subsistemas críticos do kernel Linux (CFS scheduler), Java (`TreeMap`/`TreeSet`) e C++ (`std::map`).

#### Slide 10: Repositório GitHub & Referências Bibliográficas
- Link Oficial do Repositório: https://github.com/gb-franca/amazon-engineering-challenge-trees
- Artefatos Disponíveis: Código-fonte completo, suíte de testes comparativos automatizados, JAR executável compilado e documentação técnica.
- Bibliografia de Referência:
  * CORMEN, T. H. et al. *Introduction to Algorithms (CLRS)*. 3ª ed. MIT Press, 2009.
  * ADELSON-VELSKY, G.; LANDIS, E. *An algorithm for the organization of information*. 1962.
  * SEDGEWICK, R.; WAYNE, K. *Algorithms*. 4ª ed. Addison-Wesley, 2011.

---

Por favor, gere o código completo do arquivo `apresentacao.html`, pronto para ser aberto diretamente no navegador e impresso em PDF.
```
