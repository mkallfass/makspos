import { defineStore } from "pinia";
import type { LineItem } from "@/models/pos.model";
import { PRODUCTS } from "@/data/products";
import { formatCurrency } from "@/utils";

interface State {
  products: LineItem[]
  total: number
  givenAmount: number
}

export const usePosStore = defineStore("posStore", {
  state: (): State => ({
    products: structuredClone(PRODUCTS),
    total: 0
  } as State),
  getters: {
    cartLineItems(state) {
      // Calculate line item
      let cartTotal: number = 0
      for (const l of state.products) {
        if (l.quantity) {
          l.total = l.quantity * l.price
          cartTotal = cartTotal + l.total
        }
        else {
          l.quantity = 0
          l.total = 0
        }
      }
      state.total = cartTotal
      return state.products.filter(item => item.quantity > 0)
    },
    returnAmount(state) {
      if (state.givenAmount > 0) {
        return formatCurrency(state.givenAmount - state.total)
      }
    }
  },
  actions: {
    order() {
      this.reset()
    },
    reset() {
      this.products = structuredClone(PRODUCTS);
      this.total = 0
      this.givenAmount = 0
    }
  }
})
