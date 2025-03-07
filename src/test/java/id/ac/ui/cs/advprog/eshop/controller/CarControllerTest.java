package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("name", "Toyota")
                        .param("color", "Red")
                        .param("quantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        Mockito.verify(carService).create(Mockito.any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        Car car1 = new Car();
        car1.setId(UUID.randomUUID().toString());
        car1.setName("Honda");
        car1.setColor("Blue");
        car1.setQuantity(5);

        Car car2 = new Car();
        car2.setId(UUID.randomUUID().toString());
        car2.setName("Ford");
        car2.setColor("Black");
        car2.setQuantity(8);

        Mockito.when(carService.findAll()).thenReturn(Arrays.asList(car1, car2));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testEditCarPage() throws Exception {
        String carId = UUID.randomUUID().toString();
        Car car = new Car();
        car.setId(carId);
        car.setName("Editable Car");
        car.setColor("White");
        car.setQuantity(7);

        Mockito.when(carService.findById(carId)).thenReturn(car);

        mockMvc.perform(get("/car/editCar/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCarPost() throws Exception {
        String carId = UUID.randomUUID().toString();

        mockMvc.perform(post("/car/editCar")
                        .param("id", carId)
                        .param("name", "Updated Car")
                        .param("color", "Silver")
                        .param("quantity", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        Mockito.verify(carService).update(Mockito.eq(carId), Mockito.any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        String carId = UUID.randomUUID().toString();

        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", carId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        Mockito.verify(carService).deleteCarById(carId);
    }
}
