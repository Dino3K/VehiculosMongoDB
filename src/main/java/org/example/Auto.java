package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.FindIterable;


public class Auto {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Auto() {
        // Conectar a la base de datos MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("Consecionario");
        collection = database.getCollection("Autos");
    }

    public void insertarAuto(String marca, String placa, String color, String serie, String modelo) {
        // Crear un documento para insertar
        Document auto = new Document("marca", marca)
                .append("placa", placa)
                .append("color", color)
                .append("serie", serie)
                .append("modelo", modelo);
        // Insertar el documento en la colección
        collection.insertOne(auto);
        System.out.println("Auto insertado: " + auto.toJson());
    }
    public void eliminarAuto(String placa) {
        // Crear un filtro para encontrar el auto por su placa
        Document filtro = new Document("placa", placa);
        // Eliminar el documento de la colección
        DeleteResult resultado = collection.deleteOne(filtro);
        if (resultado.getDeletedCount() > 0) {
            System.out.println("Auto eliminado con placa: " + placa);
        } else {
            System.out.println("No se encontró ningún auto con la placa: " + placa);
        }
    }
    public void actualizarAuto(String placa, String nuevoColor) {
        // Crear un filtro para encontrar el auto por su placa
        Document filtro = new Document("placa", placa);
        // Crear el documento de actualización
        Document actualizacion = new Document()
                .append("$set", new Document("color", nuevoColor)
                );
        // Actualizar el documento en la colección
        UpdateResult resultado = collection.updateOne(filtro, actualizacion);
        if (resultado.getModifiedCount() > 0) {
            System.out.println("Auto actualizado con placa: " + placa);
        } else {
            System.out.println("No se encontró ningún auto con la placa: " + placa);
        }
    }
    public void mostrarAutosPorMarca(String marca) {
        // Crear un filtro para encontrar autos por su marca
        Document filtro = new Document("marca", marca);
        // Buscar los documentos en la colección
        FindIterable<Document> autos = collection.find(filtro);
        // Mostrar solo la placa y el color de los autos encontrados
        for (Document auto : autos) {
            System.out.println("Marca: "+auto.getString("marca") +" Placa: " + auto.getString("placa") + ", Color: " + auto.getString("color"));
        }
    }


}
