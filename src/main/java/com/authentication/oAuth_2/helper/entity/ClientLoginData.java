package com.authentication.oAuth_2.helper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // у каждого класса своя таблица, но в стратегии дублируются поля логин дата как при обычном наследовании
//@Inheritance(strategy = InheritanceType.JOINED) //у ClientLoginData своя таблица, у каждого наследника своя таблица и между ними связь oneTOone
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // родитель и все наследники хранятся в одной таблице. У каждой строки будет доп столбец под названием DTYPE (столбец дискриминатора discriminator column) в котором будет запись кому принадлежат данные ( название сущности. Customer or FeedBack or Product or LoginData и т д)
//@DiscriminatorColumn(name = "Descriptor", discriminatorType = DiscriminatorType.INTEGER) // переименовываем поле DTYPE, для каждого наследника надо поставить анннотацию @DiscriminatorValue("12345")

public class ClientLoginData {
    @Id
    @GeneratedValue
    private int id;

    @Size(min = 2,max = 99)
    private String clientName;
    @Size(min = 2,max = 99)
    private String password;

    public ClientLoginData() {
    }

    public ClientLoginData(String clientName, String password) {
        this.clientName = clientName;
        this.password = password;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
