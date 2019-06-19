package com.sefa.challenge.bookrecommendationservice.controller.datareadercontroller;

/**
 * The Reader interface specifies methods for how files containing info for different classes are to be read. These
 * classes are implemented elsewehre
 *
 * @author Sefa Oduncuoglu
 */
public interface Reader {

    void readFile(String filename);

    void createObjects(String info);
}

