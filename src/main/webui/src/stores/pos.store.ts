import { defineStore } from "pinia";
import type { LineItem, Order } from "@/models/pos.model";
import { useConfigStore } from "@/stores/config.store";
import { formatCurrency } from "@/utils";

interface State {
  lineitems: LineItem[];
  total: number;
  givenAmount: number;
}

export const usePosStore = defineStore("posStore", {
  state: (): State => ({
    lineitems: [] as LineItem[],
    total: 0
  } as State),
  getters: {
    cartLineItems(state) {
      // Calculate line item
      let cartTotal: number = 0;
      for (const l of state.lineitems) {
        if (l.quantity) {
          l.total = l.quantity * l.price;
          cartTotal = cartTotal + l.total;
        } else {
          l.quantity = 0;
          l.total = 0;
        }
      }
      state.total = cartTotal;
      return state.lineitems.filter(item => item.quantity > 0);
    },
    returnAmount(state) {
      if (state.givenAmount > 0) {
        return formatCurrency(state.givenAmount - state.total, useConfigStore());
      }
    }
  },
  actions: {
    async fetchProducts() {
      const response = await fetch("api/products");
      try {
        const productListJson = await response.json();
        this.lineitems = productListJson as LineItem[];
      } catch (error) {
        this.lineitems = [] as LineItem[];
        console.error("Error loading products:", error);
        return error;
      }
    },
    order() {
      const order = { lineitems: this.cartLineItems, total: this.total } as Order;
      fetch("/api/orders", {
        method: "POST",
        body: JSON.stringify(order),
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
          "Accept": "application/json; charset=UTF-8"
        }
      })
        .then((response) => {
            if (response.ok) {
              console.info("Order successfully posted to API" + order);
            } else {
              console.error("Could not post order to API: " + response);
            }
          }
        )
        .catch((error) => {
          console.error("Error while post order to API: " + error);
        });
      this.reset();
    },
    reset() {
      this.fetchProducts();
      this.total = 0;
      this.givenAmount = 0;
    }
  }
});
