const namespaced = true;

const state = {
  items: []
}

const getters = {
  items: state => state.items
}

const actions = {
  async fetchItems({ commit }) {
    await fetch('http://localhost:8083/items')
      .then(response => response.json())
      .then(data => {
        commit('setItems', data)
      })
  },
  async createItem({ dispatch }, item) {
    return await fetch('http://localhost:8083/items', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    })
      .then(() => {
        setTimeout(dispatch('fetchItems'), 5000)
      })
  }
}

const mutations = {
  setItems(state, items) {
    state.items = items
  }
}

export default {
  namespaced,
  state,
  getters,
  actions,
  mutations
}