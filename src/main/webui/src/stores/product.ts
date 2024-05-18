import { defineStore } from "pinia";
import type { ProductSelection } from "@/models/product.model";

interface State {
  products: ProductSelection[];
}

export const useProductStore = defineStore('productStore', {
  state: (): State => {
    return {
      products:
        [
          { id: 1001, name: "Steak", detail: "mit Brötchen", price: 5 },
          { id: 1002, name: "Schweinebauch", detail: "mit Brötchen", price: 3.5 },
          { id: 1003, name: "Rote Wurst", detail: "mit Brötchen", price: 3.5 },
          { id: 1004, name: "Bratwurst", detail: "mit Brötchen", price: 3.5 },
          { id: 1005, name: "Currywurst", detail: "mit Brötchen", price: 3.5 },
          { id: 1006, name: "Maultaschen", detail: "Portion", price: 4 },
          { id: 1007, name: "Pommes Frites", detail: "Portion", price: 2.5 },
          { id: 1008, name: "Gemischter Salatteller (mit Kartoffelsalat)", detail: "Portion", price: 5 },
          { id: 1008, name: "Kartoffelsalat", detail: "Portion", price: 2.5 },
          { id: 1009, name: "Kuchen", detail: "Stück", price: 1.5 },
          { id: 2001, name: "Bier (Alpirsbacher)", detail: "0,4l", price: 3 },
          { id: 2002, name: "Radler (Alpirsbacher)", detail: "0,4l", price: 3 },
          { id: 2003, name: "Weizenbier (Alpirsbacher)", detail: "0,5l", price: 3.5 },
          { id: 2004, name: "Weizenbier alkoholfrei (Alpirsbacher)", detail: "0,5l", price: 3.5 },
          { id: 2005, name: "Weißwein (Oberkircher Müller-Thurgau, trocken)", detail: "0,25l", price: 3.5 },
          { id: 2006, name: "Rotwein (Oberkircher Spätburgunder)", detail: "0,25l", price: 3.5 },
          { id: 2007, name: "Weinschorle weiß", detail: "0,25l", price: 3 },
          { id: 2008, name: "Weinschorle rot", detail: "0,25l", price: 3 },
          { id: 2009, name: "Sommerfestbowle", detail: "0,25l", price: 3.5 },
          { id: 2010, name: "Topinambur", detail: "2cl", price: 1.5 },
          { id: 2011, name: "Williams Christ", detail: "2cl", price: 1.5 },
          { id: 2012, name: "Cola", detail: "0,33l Flasche", price: 1.5 },
          { id: 2013, name: "Fanta", detail: "0,33l Flasche", price: 1.5 },
          { id: 2014, name: "Spezi", detail: "0,5l Flasche", price: 2 },
          { id: 2015, name: "Apfelsaftschorle", detail: "0,5l Flasche", price: 2 },
          { id: 2016, name: "Mineralwasser", detail: "0,5l Flasche", price: 2 },
          { id: 2017, name: "Kaffee", detail: "Tasse", price: 1.5 }
        ] as ProductSelection[]
    }
  }
})
