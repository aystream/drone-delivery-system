package com.musala.services.medication.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DataInitializer {

    private final DataSource dataSource;

    public DataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //TODO remove this method after implementing the database and system migration
    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO medication (code, name, weight)" +
                    "VALUES ('MED_E2Z6V', 'Medication0', 402)," +
                    "       ('MED_G3WDX', 'Medication1', 473)," +
                    "       ('MED_0JF42', 'Medication2', 489)," +
                    "       ('MED_IQ3JB', 'Medication3', 295)," +
                    "       ('MED_ETPAP', 'Medication4', 171)," +
                    "       ('MED_AC7X1', 'Medication5', 183)," +
                    "       ('MED_8C8R7', 'Medication6', 223)," +
                    "       ('MED_AMULP', 'Medication7', 143)," +
                    "       ('MED_F69WZ', 'Medication8', 235)," +
                    "       ('MED_OOT5F', 'Medication9', 367)," +
                    "       ('MED_7W7RK', 'Medication10', 339)," +
                    "       ('MED_0064P', 'Medication11', 101)," +
                    "       ('MED_VKPDZ', 'Medication12', 349)," +
                    "       ('MED_W8WDJ', 'Medication13', 261)," +
                    "       ('MED_G5CYS', 'Medication14', 99)," +
                    "       ('MED_IIXXD', 'Medication15', 167)," +
                    "       ('MED_D3FA1', 'Medication16', 269)," +
                    "       ('MED_8WDVU', 'Medication17', 481)," +
                    "       ('MED_YFZY8', 'Medication18', 333)," +
                    "       ('MED_BPHY9', 'Medication19', 122)," +
                    "       ('MED_YK5U2', 'Medication20', 283)," +
                    "       ('MED_YSTF3', 'Medication21', 481)," +
                    "       ('MED_B9UOL', 'Medication22', 335)," +
                    "       ('MED_WMQAD', 'Medication23', 426)," +
                    "       ('MED_9QISW', 'Medication24', 263)," +
                    "       ('MED_L4WMX', 'Medication25', 257)," +
                    "       ('MED_G58QJ', 'Medication26', 323)," +
                    "       ('MED_X34D9', 'Medication27', 338)," +
                    "       ('MED_5A4BS', 'Medication28', 107)," +
                    "       ('MED_C4W0Y', 'Medication29', 345)," +
                    "       ('MED_JRBNV', 'Medication30', 444)," +
                    "       ('MED_Z2P51', 'Medication31', 392)," +
                    "       ('MED_B7VZW', 'Medication32', 205)," +
                    "       ('MED_C5EZD', 'Medication33', 377)," +
                    "       ('MED_6K91R', 'Medication34', 256)," +
                    "       ('MED_640D7', 'Medication35', 490)," +
                    "       ('MED_TVYI1', 'Medication36', 284)," +
                    "       ('MED_KL3RK', 'Medication37', 326)," +
                    "       ('MED_KVE4X', 'Medication38', 128)," +
                    "       ('MED_OY7P9', 'Medication39', 261)," +
                    "       ('MED_SPZDU', 'Medication40', 59)," +
                    "       ('MED_1FTES', 'Medication41', 26)," +
                    "       ('MED_0OPVP', 'Medication42', 310)," +
                    "       ('MED_48QMP', 'Medication43', 240)," +
                    "       ('MED_0BQN5', 'Medication44', 150)," +
                    "       ('MED_UG0TZ', 'Medication45', 165)," +
                    "       ('MED_X64SE', 'Medication46', 207)," +
                    "       ('MED_5WOUD', 'Medication47', 291)," +
                    "       ('MED_Y5UQD', 'Medication48', 403)," +
                    "       ('MED_9COON', 'Medication49', 390)," +
                    "       ('MED_JM4WH', 'Medication50', 409)," +
                    "       ('MED_ZU3OF', 'Medication51', 176)," +
                    "       ('MED_O9ANV', 'Medication52', 263)," +
                    "       ('MED_4WVQW', 'Medication53', 430)," +
                    "       ('MED_GS56X', 'Medication54', 291)," +
                    "       ('MED_E5CM5', 'Medication55', 428)," +
                    "       ('MED_JTZ16', 'Medication56', 206)," +
                    "       ('MED_P2OLN', 'Medication57', 498)," +
                    "       ('MED_MVE5D', 'Medication58', 341)," +
                    "       ('MED_OH1UA', 'Medication59', 39)," +
                    "       ('MED_6YFSC', 'Medication60', 12)," +
                    "       ('MED_GAQ8C', 'Medication61', 251)," +
                    "       ('MED_6JOG6', 'Medication62', 301)," +
                    "       ('MED_ORE5G', 'Medication63', 428)," +
                    "       ('MED_LFVLV', 'Medication64', 399)," +
                    "       ('MED_QXN66', 'Medication65', 11)," +
                    "       ('MED_CS70Y', 'Medication66', 336)," +
                    "       ('MED_QYAZA', 'Medication67', 335)," +
                    "       ('MED_R3BBC', 'Medication68', 60)," +
                    "       ('MED_F1044', 'Medication69', 342)," +
                    "       ('MED_NXLB6', 'Medication70', 101)," +
                    "       ('MED_GM2DZ', 'Medication71', 363)," +
                    "       ('MED_1QK4C', 'Medication72', 210)," +
                    "       ('MED_5Q24O', 'Medication73', 442)," +
                    "       ('MED_U9SGU', 'Medication74', 304)," +
                    "       ('MED_IKJHY', 'Medication75', 397)," +
                    "       ('MED_R7QXW', 'Medication76', 355)," +
                    "       ('MED_Z3X7Z', 'Medication77', 279)," +
                    "       ('MED_ECW5E', 'Medication78', 49)," +
                    "       ('MED_FNZW9', 'Medication79', 288)," +
                    "       ('MED_JR7LR', 'Medication80', 262)," +
                    "       ('MED_RWJ9F', 'Medication81', 106)," +
                    "       ('MED_GYJJE', 'Medication82', 19)," +
                    "       ('MED_DC1S9', 'Medication83', 339)," +
                    "       ('MED_49URK', 'Medication84', 492)," +
                    "       ('MED_1889I', 'Medication85', 53)," +
                    "       ('MED_RHHA7', 'Medication86', 89)," +
                    "       ('MED_XSATH', 'Medication87', 194)," +
                    "       ('MED_G9357', 'Medication88', 135)," +
                    "       ('MED_H68CH', 'Medication89', 326)," +
                    "       ('MED_I20M9', 'Medication90', 69)," +
                    "       ('MED_NS7SR', 'Medication91', 171)," +
                    "       ('MED_G24KN', 'Medication92', 234)," +
                    "       ('MED_FE8VM', 'Medication93', 188)," +
                    "       ('MED_HL0A6', 'Medication94', 171)," +
                    "       ('MED_IZPPN', 'Medication95', 406)," +
                    "       ('MED_15SPU', 'Medication96', 302)," +
                    "       ('MED_Q0GTI', 'Medication97', 102)," +
                    "       ('MED_K8GYR', 'Medication98', 87)," +
                    "       ('MED_GVZN3', 'Medication99', 317);";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}