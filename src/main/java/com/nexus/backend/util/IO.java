package com.nexus.backend.util;

import com.nexus.backend.entities.Video;

import java.io.*;
import java.util.List;

public class IO {
    public void export(List<Video> videos){
        try (
                OutputStream file = new FileOutputStream("relatorio-videos.csv");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));) {

            // Cabeçalho do arquivo CSV
            writer.write("ID;Título;Views\n");

            // Percorrendo a lista de músicas e escrevendo no arquivo
            for (int i = 0; i < videos.size(); i++) {
                Video video = videos.get(i);
                if (video != null) {
                    String linha = String.format("%s;%s;%s\n",
                            video.getId(),
                            video.getTitulo(),
                            video.getViews());
                    writer.write(linha);
                }
            }
        } catch (FileNotFoundException erro) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        }
    }

    public static void main(String[] args) {

    }
}
