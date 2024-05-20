import { defineStore } from "pinia";
import type { Config } from "@/models/config.model";
import { CONFIG } from "@/data/config";

interface State extends Config{
}

export const useConfigStore = defineStore("configStore", {
  state: (): State => ( structuredClone(CONFIG) as State)
})
