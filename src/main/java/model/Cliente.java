package model;

public class Cliente {
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;

    public Cliente(String nome, String cpfCnpj, String telefone, String email) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + " | CPF/CNPJ: " + cpfCnpj + " | Tel: " + telefone + " | Email: " + email;
    }
}
