/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcaby;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Warcaby {

    
    private int szachownica[][] = {
        {0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 0, 1, 0, 1},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {2, 0, 2, 0, 2, 0, 2, 0},
        {0, 2, 0, 2, 0, 2, 0, 2},
        {2, 0, 2, 0, 2, 0, 2, 0}

    };
    private int ruch[] = new int[2];          //tablica do przechowywania ruchów

    private final int pierwszy = 1;           //białe są reprezentowane jako 1 i 1*3
    private final int drugi = 2;               //białe są reprezentowane jako 2 i 2*3

    private int pionkiPierwszy = 0;
    private int pionkiDrugi = 0;

    int pomoc1, pomoc2, gdzieRuszycWiersz, gdzieRuszycKolumna;

    void graj() {
        this.pokazujSzachownice();
        while (true) {

            System.out.println("\033[0;1m" + "\033[31m" + "Ruch gracza pierwszego !" + " " + "\033[0m");
            this.wybor(this.pierwszy);
            this.pokazujSzachownice();
            this.wygrana();

            System.out.println("\033[0;1m" + "\033[32m" + "Ruch gracza drugiego !" + " " + "\033[0m");
            this.wybor(this.drugi);
            this.pokazujSzachownice();
            this.wygrana();

        }

    }

    void wygrana() {
        if (this.pionkiPierwszy >= 12) {
            System.out.println("WYGRA�? GRACZ PIERWSZY !!!!!! ");
            System.exit(0);
        } else if (this.pionkiDrugi >= 12) {
            System.out.println("WYGRA�? GRACZ PIERWSZY !!!!!! ");
            System.exit(0);
        }
    }

    void wprowadzWartosci() {

        boolean pomoc = true;
        Scanner odczyt;

        while (pomoc) {
            try{
            odczyt = new Scanner(System.in);
            ruch[0] = odczyt.nextInt();
            ruch[1] = odczyt.nextInt();

            if (ruch[0] < 8 && ruch[1] < 8 && ruch[0] >= 0 && ruch[1] >= 0) {
                pomoc = false;
            } else {
                
                System.out.println("Nieprawid�owe wartosci!");

            }
            }catch(InputMismatchException e){
                
                System.out.println("Poda�e� z�� warto��");
                
                
                
                
            }

        }

    }

    void wybor(int kolor) {

        boolean x = true;
        int i, j;

        do {
            do {
                this.wprowadzWartosci();
                i = ruch[0];
                j = ruch[1];
                if (this.szachownica[i][j] != kolor && this.szachownica[i][j] != kolor * 3) {   //sprawdza czy pionek/krolowa jest gracza 
                    System.out.println("To NIE jest twój pionek !");
                } else {
                    x = false;
                }

            } while (x);

            if (this.szachownica[i][j] == kolor) {
                x = wprowadzenieRuchuPionka(i, j, kolor);
            } else {
                x = wprowadzRuchKrolowej(i, j, kolor);
            }
        } while (x);
    }

    private boolean wprowadzenieRuchuPionka(int i, int j, int kolor) {
        System.out.println("Gdzie mam go przemieścić ?");
        this.wprowadzWartosci();
        gdzieRuszycWiersz = ruch[0];
        gdzieRuszycKolumna = ruch[1];
        boolean pomoc = ruchPionka(i, j, kolor);
        return pomoc;
    }

    private boolean ruchPionka(int i, int j, int kolor) {
        boolean pomoc = true;

        if (kolor == 1) {
            pomoc2 = 1;
        } else {
            pomoc2 = -1;
        }

        if (gdzieRuszycWiersz == i + pomoc2 && (gdzieRuszycKolumna == j - 1 || gdzieRuszycKolumna == j + 1)) {

            if (this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] == 0) {

                if (gdzieRuszycWiersz == 7 || gdzieRuszycWiersz == 0) {
                    this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = (kolor * 3);              //sprawdza czy pionek dotarł na ostatnią linię i jeśli tak to zaminia w królową
                } else {
                    this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = kolor;
                }

                this.szachownica[i][j] = 0;
                pomoc = false;
            } else {
                System.out.println("Pole zajęte, ponów ruch ");
            }

        } else {
            pomoc = biciePionkiem(i, j, kolor, pomoc);
        }

        return pomoc;
    }

    private boolean wprowadzRuchKrolowej(int i, int j, int kolor) {
        boolean pomoc = true;
        System.out.println("Gdzie mam go przemieścić ?");
        this.wprowadzWartosci();
        gdzieRuszycWiersz = ruch[0];
        gdzieRuszycKolumna = ruch[1];
        pomoc = ruchIBicieKrolowej(i, j, kolor);
        return pomoc;
    }

    private boolean ruchIBicieKrolowej(int i, int j, int kolor) {

        boolean pomoc = true;
        boolean ruchKrolowa = true;
        boolean bicieKrolowa = false;
        int pomoc3 = -1;
        int pomoc4 = 0;

        pomoc1 = i;
        pomoc2 = j;

        boolean sterowanie = true;                             //zmienna potrzebna do obslugi whileow w if ponizej 
        if (gdzieRuszycWiersz < i && gdzieRuszycKolumna < j) {
            while (sterowanie) {                               //w zaleznosci od zmiennych k i j sprawdza czy ruch jest wykonywany w dozwolonej drodze

                pomoc1--;
                pomoc2--;
                if (pomoc1 > 7 || pomoc1 < 0 || pomoc2 < 0 || pomoc2 > 7) {

                    ruchKrolowa = false;
                    sterowanie = false;
                } else if (this.szachownica[pomoc1][pomoc2] != 0) {
                    if (pomoc3 == -1) {

                        if (this.szachownica[pomoc1][pomoc2] != kolor && this.szachownica[pomoc1][pomoc2] != kolor * 3 && pomoc1 != gdzieRuszycWiersz && pomoc2 != gdzieRuszycKolumna) {
                            bicieKrolowa = true;
                            pomoc3 = pomoc1;
                            pomoc4 = pomoc2;
                            ruchKrolowa = false;
                        }
                    } else {
                        bicieKrolowa = false;
                    }
                    ruchKrolowa = false;
                }
                if (pomoc1 == gdzieRuszycWiersz && pomoc2 == gdzieRuszycKolumna) {
                    sterowanie = false;
                }
            }
        } else if (gdzieRuszycWiersz < i && gdzieRuszycKolumna > j) {
            while (sterowanie) {

                pomoc1--;
                pomoc2++;
                if (pomoc1 > 7 || pomoc1 < 0 || pomoc2 < 0 || pomoc2 > 7) {

                    ruchKrolowa = false;
                    sterowanie = false;
                } else if (this.szachownica[pomoc1][pomoc2] != 0) {
                    if (pomoc3 == -1) {

                        if (this.szachownica[pomoc1][pomoc2] != kolor && this.szachownica[pomoc1][pomoc2] != kolor * 3 && pomoc1 != gdzieRuszycWiersz && pomoc2 != gdzieRuszycKolumna) {
                            bicieKrolowa = true;
                            pomoc3 = pomoc1;
                            pomoc4 = pomoc2;
                            ruchKrolowa = false;
                        }
                    } else {
                        bicieKrolowa = false;
                    }
                    ruchKrolowa = false;
                }
                if (pomoc1 == gdzieRuszycWiersz && pomoc2 == gdzieRuszycKolumna) {
                    sterowanie = false;
                }

            }

        } else if (gdzieRuszycWiersz > i && gdzieRuszycKolumna > j) {
            while (sterowanie) {
                pomoc1++;
                pomoc2++;
                if (pomoc1 > 7 || pomoc1 < 0 || pomoc2 < 0 || pomoc2 > 7) {

                    ruchKrolowa = false;
                    sterowanie = false;
                } else if (this.szachownica[pomoc1][pomoc2] != 0) {
                    if (pomoc3 == -1) {

                        if (this.szachownica[pomoc1][pomoc2] != kolor && this.szachownica[pomoc1][pomoc2] != kolor * 3 && pomoc1 != gdzieRuszycWiersz && pomoc2 != gdzieRuszycKolumna) {
                            bicieKrolowa = true;
                            pomoc3 = pomoc1;
                            pomoc4 = pomoc2;
                            ruchKrolowa = false;
                        }
                    } else {
                        bicieKrolowa = false;
                    }
                    ruchKrolowa = false;
                }
                if (pomoc1 == gdzieRuszycWiersz && pomoc2 == gdzieRuszycKolumna) {
                    sterowanie = false;
                }

            }
        } else if (gdzieRuszycWiersz > i && gdzieRuszycKolumna < j) {
            while (sterowanie) {

                pomoc1++;
                pomoc2--;
                if (pomoc1 > 7 || pomoc1 < 0 || pomoc2 < 0 || pomoc2 > 7) {

                    ruchKrolowa = false;
                    sterowanie = false;
                } else if (this.szachownica[pomoc1][pomoc2] != 0) {
                    if (pomoc3 == -1) {

                        if (this.szachownica[pomoc1][pomoc2] != kolor && this.szachownica[pomoc1][pomoc2] != kolor * 3 && pomoc1 != gdzieRuszycWiersz && pomoc2 != gdzieRuszycKolumna) {
                            bicieKrolowa = true;
                            pomoc3 = pomoc1;
                            pomoc4 = pomoc2;
                            ruchKrolowa = false;
                        }
                    } else {
                        bicieKrolowa = false;
                    }
                    ruchKrolowa = false;
                }
                if (pomoc1 == gdzieRuszycWiersz && pomoc2 == gdzieRuszycKolumna) {
                    sterowanie = false;
                }

            }
        } else {
            if (this.szachownica[pomoc1][pomoc2] != 0) {
                ruchKrolowa = false;

            }

        }

        if (ruchKrolowa) {
            this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = this.szachownica[i][j];
            this.szachownica[i][j] = 0;
            pomoc = false;
        } else if (bicieKrolowa) {
            System.out.println("KROLOWA");
            this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = this.szachownica[i][j];
            this.szachownica[i][j] = 0;
            this.szachownica[pomoc3][pomoc4] = 0;
            pomoc = false;
            dodawanieZbic(kolor);
            czyIstniejeKolejneBicieKrolowej(gdzieRuszycWiersz, gdzieRuszycKolumna, kolor);                  //sprawdza, czy po biciu jest mozliwe kolejne bicie
        } else {
            System.out.println("Błędny ruch !!!!!!!");
            this.pokazujSzachownice();
        }

        return pomoc;

    }

    private void pokazujSzachownice() {
        System.out.print("\033[35m" + "   0 1 2 3 4 5 6 7" + "\033[0m");
        for (int i = 0; i < 8; i++) {
            System.out.print("\n\033[35m" + i + "  " + "\033[0m");
            for (int j = 0; j < 8; j++) {
                int x = szachownica[i][j];
                if (x != 0) {                                                                                  //opcja z if'em - bez break; - ktory podobno masakruje procesor
                    if (x == 1 || x == 3) {
                        System.out.print("\033[0;1m" + "\033[31m" + szachownica[i][j] + " " + "\033[0m");    //dodana opcja koloru dla damek
                    } else {
                        System.out.print("\033[0;1m" + "\033[32m" + szachownica[i][j] + " " + "\033[0m");
                    }
                } else {
                    System.out.print(szachownica[i][j] + " ");
                }

            }
        }
        System.out.println("\n");
    }

    private void dodawanieZbic(int kolor) {     //dodaje pionki po ich zbiciu do wyniku - uzywana po biciu pionka i krolowej
        if (kolor == 1 || kolor == 3) {
            this.pionkiPierwszy++;
        } else {
            this.pionkiDrugi++;
        }
        System.out.println("LIczba zbitych pionków czarnych " + this.pionkiPierwszy + " Liczba zbitych pionków białych " + this.pionkiDrugi);
    }

    public boolean biciePionkiem(int i, int j, int kolor, boolean pomoc) {          //bicie pionkiem
        if (((gdzieRuszycWiersz == i - 2) || (gdzieRuszycWiersz == i + 2)) && (gdzieRuszycKolumna == j - 2 || gdzieRuszycKolumna == j + 2)) {   //Sprawdza czy ruch jest wykonany o dwa pola w dół lub w gore i czy jest od dwa pola w lewo lub w prawo
            if (this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] == 0) {                            //jesli to pole jest wolne to konntynuluje a jesli nie to wymusza nowy ruch
                int h;                                   //zmienna ktora okresli czy badamy pole po lewej czy po prawej 
                if (j < gdzieRuszycKolumna) {
                    h = gdzieRuszycKolumna - 1;
                } else {
                    h = gdzieRuszycKolumna + 1;
                }

                if (i < gdzieRuszycWiersz) {
                    pomoc2 = 1;
                } else {
                    pomoc2 = -1;
                }

                if (this.szachownica[i + pomoc2][h] != kolor && this.szachownica[i + pomoc2][h] != 0) {  //Sprawdza czy pole zawiera pionek pczeciwnika i nie jest polem pustym
                    this.szachownica[i][j] = 0;                                        // jesli pole zawiera pionek przeciwnika to zeruje aktualnie zajmowane pole 
                    this.szachownica[i + pomoc2][h] = 0;                                 //zeruje pole zajmowane przez pionek przeciwnika

                    if (kolor == 1 && gdzieRuszycWiersz == 7) {
                        this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = (kolor * 3);       //sprawdza czy pionek dotarł na ostatnią linię i jeśli tak to zaminia w królową
                    } else if (kolor == 2 && gdzieRuszycWiersz == 0) {
                        this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = (kolor * 3);
                    } else {
                        this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] = kolor;
                    }

                    pomoc = false;
                    dodawanieZbic(kolor);
                   
                    if (this.szachownica[gdzieRuszycWiersz][gdzieRuszycKolumna] == (kolor * 3)) {
                       
                        czyIstniejeKolejneBicieKrolowej(gdzieRuszycWiersz, gdzieRuszycKolumna, kolor);
                       
                    } else {
                        pionkiemBicieWielokrotne(kolor);          
                    }
                } else {
                    System.out.println("Niedozwolony ruch(bicie) !");
                }

            } else {
                System.out.println("Niedozwolony ruch 2!");
            }
        } else {
            System.out.println("Niedozwolony ruch 1!");
        }
        return pomoc;
    }

    private void pionkiemBicieWielokrotne(int kolor) {     //sprawdza, czy mozliwe jest kolejne bicie, wartosci startowe to punk docelowy pionka po 1 biciu
        boolean a1 = false, a2 = true;                                          //sprawdza czy mozliwe jest kolejne bicie, jesli tak, wtedy uruchamia ponownie metode 'bicie'
        int b = gdzieRuszycWiersz, c = gdzieRuszycKolumna, d1 = 0, d2 = 0, d3 = 0, d4 = 0, d5 = 0, d6 = 0, d7 = 0, d8 = 0;       //sprawdza czy istnieje jesli tak to zmienia boola i ustawia wartosci dx do sprawdzenia

        if (0 <= gdzieRuszycWiersz - 2 && 0 <= gdzieRuszycKolumna - 2) {                                     //1/4 - warunek sprawdza, czy pole nie jest poza tablica (pola poza tab, nie maja zadnej wartosci...)
            if (this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna - 1] != kolor && this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna - 1] != kolor * 3 && this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna - 1] != 0
                    && this.szachownica[gdzieRuszycWiersz - 2][gdzieRuszycKolumna - 2] == 0) {               //sprawdza 1/4 lini, czy mozna wykonac bicie, jesli tak nadaje wartosci d1 i d2 od przyrownania i zmienia flage boola, ktora zacznie pentle while nizej
                a1 = true;
                d1 = gdzieRuszycWiersz - 2;
                d2 = gdzieRuszycKolumna - 2;
            }
        }
        if (gdzieRuszycWiersz + 2 < 8 && 0 <= gdzieRuszycKolumna - 2) {                                      // linia 2/4, opis warunku powyzej
            if (this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna - 1] != kolor && this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna - 1] != kolor * 3 && this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna - 1] != 0
                    && this.szachownica[gdzieRuszycWiersz + 2][gdzieRuszycKolumna - 2] == 0) {
                a1 = true;
                d3 = gdzieRuszycWiersz + 2;
                d4 = gdzieRuszycKolumna - 2;
            }
        }
        if (gdzieRuszycWiersz + 2 < 8 && gdzieRuszycKolumna + 2 < 8) {
            if (this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna + 1] != kolor && this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna + 1] != kolor * 3 && this.szachownica[gdzieRuszycWiersz + 1][gdzieRuszycKolumna + 1] != 0
                    && this.szachownica[gdzieRuszycWiersz + 2][gdzieRuszycKolumna + 2] == 0) {
                a1 = true;
                d5 = gdzieRuszycWiersz + 2;
                d6 = gdzieRuszycKolumna + 2;
            }
        }
        if (0 <= gdzieRuszycWiersz - 2 && gdzieRuszycKolumna + 2 < 8) {
            if (this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna + 1] != kolor && this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna + 1] != kolor * 3 && this.szachownica[gdzieRuszycWiersz - 1][gdzieRuszycKolumna + 1] != 0
                    && this.szachownica[gdzieRuszycWiersz - 2][gdzieRuszycKolumna + 2] == 0) {
                a1 = true;
                d7 = gdzieRuszycWiersz - 2;
                d8 = gdzieRuszycKolumna + 2;
            }
        }

        if (a1) {                   //bool zmieniony, a wiec jest drugie bicie, wykonuje je i blokuje jesli wprowadzisz zle wartosci
            pokazujSzachownice();
            System.out.println("Jest mozliwe kolejne bicie, wykonaj je!");
            do {                    //druga pentla sprawdza, czy dobre wartosci podalismy, do ponownego bicia, wartosci d ktore zostaly nadane w if'ach wyzej musza sie rownac ruchowi jaki chcemy wykonac
                wprowadzWartosci();
                gdzieRuszycWiersz = ruch[0];
                gdzieRuszycKolumna = ruch[1];
                if ((gdzieRuszycWiersz == d1 || gdzieRuszycWiersz == d3 || gdzieRuszycWiersz == d5 || gdzieRuszycWiersz == d7) && (gdzieRuszycKolumna == d2 || gdzieRuszycKolumna == d4 || gdzieRuszycKolumna == d6 || gdzieRuszycKolumna == d8)) {
                    a2 = false;
                } else {
                    System.out.println("Nieprawidlowy ruch! Wykonaj ponownie!");
                }
            } while (a2);
            ruchPionka(b, c, kolor);
        }
    }

    private void czyIstniejeKolejneBicieKrolowej(int k, int l, int kolor) {     //wywoluje metode "pentelka" zmieniajac pierwsze 2 parametry,
        pentelkaSprawdzajacaKolejneBicieKrolowej(1, 1, k, l, kolor);                                        //dzieki czemu robimy ruch w kazdym kierunku po skosie
      
        pentelkaSprawdzajacaKolejneBicieKrolowej(-1, 1, k, l, kolor);
       
        pentelkaSprawdzajacaKolejneBicieKrolowej(1, -1, k, l, kolor);
      
        pentelkaSprawdzajacaKolejneBicieKrolowej(-1, -1, k, l, kolor);
        

    }

    private void pentelkaSprawdzajacaKolejneBicieKrolowej(int a, int b, int k1, int l1, int kolor) {      //sprawdza czy na drodze jest pionek przeciwnika, jesli tak to sprawdza
        int j = l1, pomo=0;                                                        //czy nastepne pole za pionkiem jest wolne, jesli znowu tak, to wywoluje    
        boolean x = true, y = true;
       
        for (int i = k1; 0 <= i && i <= 7; i += a) {                                 //metode krolowa z info, ze mozliwe jest kolejne bicie
    
            if (this.szachownica[i][j] == kolor || this.szachownica[i][j] == kolor * 3){
                pomo++;
                if (pomo>=2) i=7;
            
            }
            if (this.szachownica[i][j] != kolor && this.szachownica[i][j] != kolor * 3 && this.szachownica[i][j] != 0 && 0 < i && i < 7 && 0 < j && j < 7) {
                if (this.szachownica[i + a][j + b] == 0) {
                    jestKolejneBicieKrolowej(k1, l1, kolor);
                } else {
                    i = -10;
                }
            }
            System.out.println(i+" "+j);
            if (0 < j && j < 7) {
                j += b;
            } else {
                i = -10;
            }
        }
    }

    private void jestKolejneBicieKrolowej(int k1, int l1, int kolor) {
        boolean y = true;
        int k2, l2;
        System.out.println("Jest mozliwe kolejne bicie");
        pokazujSzachownice();
        System.out.println("Wykonaj je!");
        while (y) {
            this.wprowadzWartosci();
            gdzieRuszycWiersz = ruch[0];
            gdzieRuszycKolumna = ruch[1];

        
            k2 = gdzieRuszycWiersz - k1;
            l2 = gdzieRuszycKolumna - l1;
          
            if (0 < k2 && 0 < l2) {                        //++       
                y = sprawdzWartosciKolejnegoBiciaKrolowej(1, 1, k1, l1, kolor);
            } else if (0 < k2 && 0 > l2) {                     //+-
                y = sprawdzWartosciKolejnegoBiciaKrolowej(1, -1, k1, l1, kolor);
            } else if (0 > k2 && 0 < l2) {                     //-+
                y = sprawdzWartosciKolejnegoBiciaKrolowej(-1, 1, k1, l1, kolor);
            } else if (0 > k2 && 0 > l2) {                  //--
                y = sprawdzWartosciKolejnegoBiciaKrolowej(-1, -1, k1, l1, kolor);
            }
            if (y) {
                System.out.println("Niepoprawny ruch, wykonaj ponownie!");
            }
        }
        ruchIBicieKrolowej(k1, l1, kolor);
    }

    private boolean sprawdzWartosciKolejnegoBiciaKrolowej(int a, int b, int k1, int j, int kolor) {
        int c = 0, d = 0;
        for (int i = k1; 0 <= i && i <= 7; i += a) {
            if (this.szachownica[i][j] != 0) {
                d++;
            }
            if (this.szachownica[i][j] != kolor && this.szachownica[i][j] != kolor * 3 && this.szachownica[i][j] != 0) {
                c++;
            }
            if (i == gdzieRuszycWiersz && j == gdzieRuszycKolumna && 0 < c && c < 2 && d < 3) {
                return false;
            }
            if (i == gdzieRuszycWiersz) {
                i = -10;
            }
            j += b;
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Warcaby x = new Warcaby();
        x.graj();

    }

}
