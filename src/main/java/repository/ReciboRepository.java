package repository;

import adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Recibo;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReciboRepository {
    private static final String CAMINHO_ARQUIVO = "recibos.json";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public void salvarTodos(List<Recibo> recibos){
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)){
            gson.toJson(recibos, writer);
            System.out.println("Recibos salvos com sucesso!");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Recibo> carregarTodos() {
        try {
            File file = new File(CAMINHO_ARQUIVO);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<List<Recibo>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void salvar(Recibo recibo) {
        List<Recibo> recibos = carregarTodos();
        recibos.add(recibo);
        salvarTodos(recibos);
    }

    public List<Recibo> filtrarPorCpf(String cpfBusca){
        List<Recibo> todos = carregarTodos();
        List<Recibo> filtrados = new ArrayList<>();

        for (Recibo r : todos){
            String cpf = r.getCliente().getCpfCnpj();
            if (cpf.contains(cpfBusca)){
                filtrados.add(r);
            }
        }
        return filtrados;
    }
}
