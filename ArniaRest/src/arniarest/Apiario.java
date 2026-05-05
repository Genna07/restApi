/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arniarest;

public class Apiario {
    private int id;
    private String nome;
    private String posizione;

    public Apiario(int id, String nome, String posizione) {
        this.id = id;
        this.nome = nome;
        this.posizione = posizione;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getPosizione() { return posizione; }
}