package ru.se.ifmo.s466351.lab6.server;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.exception.CommandException;
import ru.se.ifmo.s466351.lab6.server.exception.MovieDequeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;


public class SaveManager {
    JAXBContext context;
    Marshaller marshaller;
    Unmarshaller unmarshaller;
    File file;

    public SaveManager(String filePath) {
        file = new File(filePath);
        try {
            context = JAXBContext.newInstance(MovieDeque.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("Ошибка при сериализации/десериализации");
        }
    }

    public String getFileName() {
        return file.getName();
    }

    public void saveInXML(MovieDeque movies) {
        prepareSaveFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(convertToXML(movies).toString());
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении");
        }
    }

    public MovieDeque loadFromXML() {
        if (!file.exists()) return new MovieDeque();
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder xmlContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                xmlContent.append(scanner.nextLine()).append("\n");
            }
            if (xmlContent.isEmpty()) {
                return new MovieDeque();
            }
            StringReader stringReader = new StringReader(xmlContent.toString());
            MovieDeque result = (MovieDeque) unmarshaller.unmarshal(stringReader);
            if (result == null) return new MovieDeque();

            result.manageDeque();
            return result;
        } catch (JAXBException | FileNotFoundException e) {
            throw new MovieDequeException("Возможно XML файл повреждён. " + file.getAbsolutePath());
        }
    }

    public StringWriter convertToXML(MovieDeque movies) throws CommandException {
        try {
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(movies, stringWriter);
            return stringWriter;
        } catch (JAXBException e) {
            throw new CommandException("Ошибка при сериализации.");
        }
    }

    private void prepareSaveFile() throws CommandException {
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("Создан новый файл сохранения");
                }
            }
        } catch (IOException e) {
            throw new CommandException("Ошибка при создании файла");
        }
    }
}
