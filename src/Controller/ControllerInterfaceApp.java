/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.User;

/**
 *
 * @author tbruce
 */
public interface ControllerInterfaceApp {
    public abstract void preloadData(User user, Appointment appointment);
}
