package ru.itis.taskmanager.chat;

import org.springframework.stereotype.Component;
import ru.itis.taskmanager.dto.MessageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Chat {
    private final List<Client> clients = new ArrayList<>();

    public void join(Client newClient) {
        clients.add(newClient);
    }

    public boolean isPresent(Client foundClient) {
        return clients.stream()
                .filter(client -> client.getUserId().equals(foundClient.getUserId())
                        && client.getBoardId().equals(foundClient.getBoardId()))
                .collect(Collectors.toList()).size() > 0;
    }

    public Client getClient(Client foundClient) {
        if (isPresent(foundClient)) {
            return clients.stream()
                    .filter(client -> client.getUserId().equals(foundClient.getUserId())
                            && client.getBoardId().equals(foundClient.getBoardId()))
                    .collect(Collectors.toList()).get(0);
        }
        return null;
    }

    public List<MessageDto> observe(Observer observer, Client client) throws InterruptedException {
        Client foundClient = getClient(client);
        if (foundClient != null) {
            synchronized (foundClient) {
                foundClient.wait();
            }
            return observer.onChange();
        }
        return new ArrayList<>();
    }

    public void onChange(Client consumer) {
        for (Client client : clients) {
            synchronized (client) {
                if (client.getBoardId().equals(consumer.getBoardId()) && !client.getUserId().equals(consumer.getUserId())) {
                    client.notify();
                }
            }
        }
    }

    public interface Observer {
        List<MessageDto> onChange();
    }
}
