/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.LendingsDetailed;
import java.util.List;

public interface LendingsDetailedService {
    List<LendingsDetailed> findPendingLendingsByUserName(String userName);
}