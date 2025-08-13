package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Cliente;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private static final String CAMINHO_ARQUIVO = "clientes.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarTodos(List<Cliente> clientes) {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(clientes, writer);
            System.out.println("Clientes salvos com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> carregarTodos() {
        try {
            File file = new File(CAMINHO_ARQUIVO);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<List<Cliente>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void salvar(Cliente cliente) {
        List<Cliente> clientes = carregarTodos();
        clientes.add(cliente);
        salvarTodos(clientes);
    }

    // ✅ Novo método para listar todos os clientes
    public List<Cliente> listarTodos() {
        return carregarTodos();
    }
}
