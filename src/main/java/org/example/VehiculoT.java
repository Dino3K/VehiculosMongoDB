package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
public class VehiculoT extends Auto{
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public VehiculoT() {
        // Conectar a la base de datos MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("Consecionario");
        collection = database.getCollection("Autos");
    }
    public void insertarAutoT(String marca,String tipo, String placa, String color, String serie, String modelo,String numeroLlantas) {
        Document auto = new Document("marca", marca)
                .append("Tipo", tipo)
                .append("placa", placa)
                .append("color", color)
                .append("serie", serie)
                .append("modelo", modelo)
                .append("numeroLlantas", numeroLlantas);
        // Insertar el documento en la colección
        collection.insertOne(auto);
        System.out.println("Auto insertado: " + auto.toJson());
    }

    @Override
    public void eliminarAuto(String placa) {
        super.eliminarAuto(placa);
    }

    @Override
    public void actualizarAuto(String placa, String nuevoColor) {
        super.actualizarAuto(placa, nuevoColor);
    }

    @Override
    public void mostrarAutosPorMarca(String marca) {
        // Crear un filtro para encontrar autos por su marca
        Document filtro = new Document("marca", marca);
        // Buscar los documentos en la colección
        FindIterable<Document> autos = collection.find(filtro);
        // Mostrar solo la placa y el color de los autos encontrados
        for (Document auto : autos) {
            System.out.println("Marca: "+auto.getString("marca") +" Placa: " + auto.getString("placa") + ", Color: " + auto.getString("color" )+", Llantas: "+auto.getString("numeroLlantas"));
        }

    }

}
