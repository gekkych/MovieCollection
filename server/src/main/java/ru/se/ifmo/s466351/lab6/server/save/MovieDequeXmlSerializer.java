package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class MovieDequeXmlSerializer implements Serializer<MovieDeque> {

    @Override
    public String serialize(MovieDeque collectionWrapper) {
        try {
            JAXBContext context = JAXBContext.newInstance(MovieDeque.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            marshaller.marshal(collectionWrapper, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка сериализации в XML", e);
        }
    }

    @Override
    public MovieDeque deserialize(String string) {
        try {
            if (string == null || string.isEmpty()) return new MovieDeque();
            JAXBContext context = JAXBContext.newInstance(MovieDeque.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(string);
            return (MovieDeque) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка десериализации из XML", e);
        }
    }
}
