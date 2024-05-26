import { defineStore } from "pinia";
import type { LineItem } from "@/models/pos.model";
import { useConfigStore } from "@/stores/config.store";
import { formatCurrency } from "@/utils";

interface State {
  products: LineItem[]
  total: number
  givenAmount: number
}

export const usePosStore = defineStore("posStore", {
  state: (): State => ({
    products: [] as LineItem[],
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
        return formatCurrency(state.givenAmount - state.total, useConfigStore())
      }
    }
  },
  actions: {
    async fetchProducts(){
      const response = await fetch("api/products")
      try {
        const productListJson = await response.json()
        this.products = productListJson as LineItem[]
      }
      catch (error) {
        this.products = [] as LineItem[]
        console.error("Error loading products:", error)
        return error
      }
    },
    order() {
      this.reset()
    },
    reset() {
      this.fetchProducts()
      this.total = 0
      this.givenAmount = 0
      }
  }
})
