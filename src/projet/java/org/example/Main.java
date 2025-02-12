package org.example;

import gestion_satde.entities.Stade;
import gestion_satde.entities.evenement;
import gestion_satde.services.serviceEvenement;
import gestion_satde.services.serviceStade;
import gestion_satde.tools.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataSource.getInstance();
        serviceStade ss = new serviceStade();
        Stade st = new Stade("sousse",30.500,"//");
       // List<Stade> t = new ArrayList<>();
        Stade st2 = new Stade();
        st2=ss.getOne(2);
        System.out.println(st2);
        serviceEvenement se = new serviceEvenement();
        LocalDate eventDate = LocalDate.of(2025, 5, 20);

        evenement e= new evenement("match1","football",eventDate,"federation",50.000,2);
        se.modifier(e,1);
    }
}