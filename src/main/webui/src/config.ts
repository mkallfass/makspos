import type { Config } from '@/models/config.model'

export const fetchConfig = async () => {
  const response = await fetch('/api/config')
  const json = await response.json()
  sessionStorage.setItem('config', JSON.stringify(json))
  console.info('Got config from API and stored in session storage')
}

// unused
export async function fetchConfigAsync() {
  fetch('/api/config')
    .then((response) => {
      if (response.ok) {
        console.info('Got config successfully from API')
      } else {
        console.error('Could not get config from API: ' + response)
        throw new Error(response.statusText)
      }
      return response.json()
    })
    .then((json) => {
      // console.debug("Got config json from API: " + JSON.stringify(json));
      sessionStorage.setItem('config', JSON.stringify(json))
    })
    .catch((error) => {
      console.error('Error loading config:', error)
    })
}

export const getConfig = () => {
  if (sessionStorage.getItem('config') === null) {
    console.error('Error getting config from session storage')
    return {} as Config
  }
  return jsonToConfig(sessionStorage.getItem('config')!!)
}

function jsonToConfig(json: string): Config {
  const obj = JSON.parse(json)
  // console.debug("labels: " + JSON.stringify(Object.entries(obj.labels)))
  const configLabels = new Map<string, string>()
  for (const e of Object.entries(obj.labels)) {
    configLabels.set(e[0], e[1] as string)
  }
  const config = JSON.parse(json)
  config.labels = configLabels
  // console.debug("config as object: " + JSON.stringify(config))
  return config
}
