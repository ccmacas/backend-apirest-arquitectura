/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.celia.cripto.app;

/**
 *
 * @author Celia Macas
 */
class Hotel {

    public String name;
    public HotelAddress hotelAddress;

    public Hotel(String name, HotelAddress hotelAddress) {
        this.name = name;
        this.hotelAddress = hotelAddress;
    }

    public Hotel(String name, String street, String city, String province) {
        this.name = name;
        this.hotelAddress = new HotelAddress(street, city, province);
    }
}
