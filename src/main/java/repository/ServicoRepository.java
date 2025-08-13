package repository;

import adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Servico;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {
    private static final String CAMINHO_ARQUIVO = "servicos.json";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public void salvarTodos(List<Servico> servicos) {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(servicos, writer);
            System.out.println("Serviços salvos com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Servico> carregarTodos() {
        try {
            File file = new File(CAMINHO_ARQUIVO);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<List<Servico>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void salvar(Servico servico) {
        List<Servico> servicos = carregarTodos();
        servicos.add(servico);
        salvarTodos(servicos);
    }

    // ✅ Novo método para listar todos os serviços
    public List<Servico> listarTodos() {
        return carregarTodos();
    }
}
