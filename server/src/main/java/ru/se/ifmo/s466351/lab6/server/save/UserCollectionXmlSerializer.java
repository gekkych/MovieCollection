package ru.se.ifmo.s466351.lab6.server.save;

import ru.se.ifmo.s466351.lab6.server.user.User;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class UserCollectionXmlSerializer implements Serializer<UserCollection>{

    @Override
    public String serialize(UserCollection collectionWrapper) {
        try {
            JAXBContext context = JAXBContext.newInstance(UserCollection.class);
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
    public UserCollection deserialize(String string) {
        try {
            if (string == null || string.isEmpty()) {
                UserCollection userCollection = new UserCollection();
                userCollection.add(new User("admin", "0"));
                return userCollection;
            }
            JAXBContext context = JAXBContext.newInstance(UserCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(string);
            UserCollection collection = (UserCollection) unmarshaller.unmarshal(reader);
            User admin = new User("admin", "0");
            if (!collection.has(admin)) {
                collection.add(admin);
            }
            return (UserCollection) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка десериализации из XML", e);
        }
    }
}
