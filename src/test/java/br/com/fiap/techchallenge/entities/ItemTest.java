package br.com.fiap.techchallenge.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste da entidade Item")
class ItemTest {

    private Item item;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        item = new Item();
        restaurante = new Restaurante();
    }

    @Test
    @DisplayName("Deve criar um Item com sucesso")
    void deveCriarUmItem() {
        // Arrange
        Long id = 1L;
        String nome = "Frango a Parmegiana";
        String descricao = "Frango coberto com molho de tomate e queijo, acompanhado de arroz e batata frita";
        BigDecimal preco = new BigDecimal("81.90");
        String disponibilidade = "Sim";
        String imagem = "/caminho/da/foto.jpg";

        // Act
        item.setId(id);
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setDisponibilidade(disponibilidade);
        item.setImagem(imagem);
        item.setRestaurante(restaurante);

        // Assert
        assertEquals(id, item.getId());
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(preco, item.getPreco());
        assertEquals(disponibilidade, item.getDisponibilidade());
        assertEquals(imagem, item.getImagem());
        assertEquals(restaurante, item.getRestaurante());
    }

    @Test
    @DisplayName("Deve validar o ID do item")
    void deveValidarId() {
        // Arrange
        Long id = 999L;

        // Act
        item.setId(id);

        // Assert
        assertNotNull(item.getId());
        assertEquals(id, item.getId());
    }

    @Test
    @DisplayName("Deve validar o nome do item")
    void deveValidarNome() {
        // Arrange
        String nome = "Pizza Margherita";

        // Act
        item.setNome(nome);

        // Assert
        assertNotNull(item.getNome());
        assertEquals(nome, item.getNome());
        assertFalse(item.getNome().isEmpty());
    }

    @Test
    @DisplayName("Deve validar a descrição do item")
    void deveValidarDescricao() {
        // Arrange
        String descricao = "Pizza tradicional com molho de tomate, mozzarela e manjericão";

        // Act
        item.setDescricao(descricao);

        // Assert
        assertNotNull(item.getDescricao());
        assertEquals(descricao, item.getDescricao());
    }

    @Test
    @DisplayName("Deve validar o preço do item")
    void deveValidarPreco() {
        // Arrange
        BigDecimal preco = new BigDecimal("45.50");

        // Act
        item.setPreco(preco);

        // Assert
        assertNotNull(item.getPreco());
        assertEquals(preco, item.getPreco());
        assertTrue(item.getPreco().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Deve validar a disponibilidade do item")
    void deveValidarDisponibilidade() {
        // Arrange
        String disponibilidade = "Sim";

        // Act
        item.setDisponibilidade(disponibilidade);

        // Assert
        assertNotNull(item.getDisponibilidade());
        assertEquals(disponibilidade, item.getDisponibilidade());
    }

    @Test
    @DisplayName("Deve validar a imagem do item")
    void deveValidarImagem() {
        // Arrange
        String imagem = "/imagens/frango-parmegiana.jpg";

        // Act
        item.setImagem(imagem);

        // Assert
        assertNotNull(item.getImagem());
        assertEquals(imagem, item.getImagem());
    }

    @Test
    @DisplayName("Deve validar a relação com Restaurante")
    void deveValidarRelacaoComRestaurante() {
        // Arrange & Act
        item.setRestaurante(restaurante);

        // Assert
        assertNotNull(item.getRestaurante());
        assertEquals(restaurante, item.getRestaurante());
    }

    @Test
    @DisplayName("Deve permitir restaurante nulo")
    void devePermitirRestauranteNulo() {
        // Arrange & Act
        item.setRestaurante(null);

        // Assert
        assertNull(item.getRestaurante());
    }

    @Test
    @DisplayName("Deve atualizar valores do item")
    void deveAtualizarValores() {
        // Arrange
        item.setNome("Item Original");
        String novoNome = "Item Atualizado";
        BigDecimal preco = new BigDecimal("100.00");

        // Act
        item.setNome(novoNome);
        item.setPreco(preco);

        // Assert
        assertEquals(novoNome, item.getNome());
        assertEquals(preco, item.getPreco());
        assertNotEquals("Item Original", item.getNome());
    }

    @Test
    @DisplayName("Deve comparar dois items com mesmos valores")
    void deveCompararDoisItems() {
        // Arrange
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setId(1L);
        item1.setNome("Hambúrguer");
        item2.setId(1L);
        item2.setNome("Hambúrguer");

        // Assert
        assertEquals(item1.getId(), item2.getId());
        assertEquals(item1.getNome(), item2.getNome());
    }
}