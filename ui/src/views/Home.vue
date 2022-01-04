<template>
  <ValidationObserver v-slot="{ invalid }">
    <v-container>
      <v-row class="pa-4">
        <v-col cols="12">
          <v-row>
            <v-col cols="auto" class="flex-grow-1 d-flex align-center">
              <span>Available Games: </span>
              <v-chip color="success" x-small class="mx-1">Runescape</v-chip>
              <v-chip color="success" x-small class="mx-1">Cyberpunk 2077</v-chip>
              <v-chip color="success" x-small class="mx-1">Zelda: Breath of the Wild</v-chip>
            </v-col>
            <v-col>
              <v-btn depressed color="primary" small>Create Item</v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="12">
          <v-data-table
            :headers="headers"
            :items="items"
            :items-per-page="5"
            :loading="loading"
            class="elevation-1"
          ></v-data-table>
        </v-col>
      </v-row>
      <v-dialog v-model="createItemModal" width="400">
        <v-card>
          <v-card-title>
            Create Item
          </v-card-title>
          <v-card-subtitle>
            Fill in the details to generate an item for the supported games
          </v-card-subtitle>
          <v-divider></v-divider>
          <v-card-text>
            <v-container>
              <v-form>
                <ValidationProvider
                  name="itemType"
                  :rules="rules.itemType"
                  v-slot="validationContext"
                >
                  <v-select
                    v-model="newItem.type"
                    label="Item Type"
                    outlined
                    dense
                    :items="itemTypes"
                    :error="getValidationState(validationContext)"
                    :error-messages="validationContext.errors[0]"
                    :loading="loading"
                  ></v-select>
                </ValidationProvider>
                <ValidationProvider 
                  name="itemGrade"
                  :rules="rules.itemGrade"
                  v-slot="validationContext"
                >
                  <v-select
                    v-model="newItem.grade"
                    label="Item Grade"
                    outlined
                    dense
                    :items="itemGrades"
                    :error="getValidationState(validationContext)"
                    :error-messages="validationContext.errors[0]"
                    :loading="loading"
                  ></v-select>
                </ValidationProvider>
                <ValidationProvider
                  name="itemName"
                  :rules="rules.itemName"
                  v-slot="validationContext"
                >
                  <v-text-field
                    v-model="newItem.name"
                    label="Item Name"
                    outlined
                    :error="getValidationState(validationContext)"
                    :error-messages="validationContext.errors[0]"
                    :loading="loading"
                  ></v-text-field>
                </ValidationProvider>
              </v-form>
            </v-container>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              depressed
              color="error"
              :loading="loading"
              @click="createItemModal = false"
            >
              Cancel
            </v-btn>
            <v-btn
              depressed
              color="success"
              :disabled="invalid"
              :loading="loading"
              @click="handleCreateItem"
            >
              Submit
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    <v-snackbar
      v-model="showSnackbar"
      color="success"
      :timeout="2000"
      right
      top
    >
      <span class="font-weight-bold">Item creation submitted!</span>
    </v-snackbar>
  </ValidationObserver>
</template>

<script>
  import { ValidationObserver, ValidationProvider } from 'vee-validate'
  import { mapActions, mapGetters } from 'vuex'
  export default {
    name: 'Home',
    components: { ValidationObserver, ValidationProvider},
    data: () => ({
      headers: [
        {
          text: 'Game Reference',
          value: 'gameReference'
        },
        {
          text: 'Name',
          value: 'name'
        },
        {
          text: 'Damage',
          value: 'stats.damage'
        },
        {
          text: 'Damage Type',
          value: 'stats.damageType'
        },
        {
          text: 'Durability',
          value: 'stats.durability'
        }
      ],
      itemTypes: ['Melee', 'Sword', 'Gun', 'Ranged', 'Magic', 'Cyberware', 'Bow', 'Spear'],
      itemGrades: ['S', 'B', 'C', 'D', 'F'],
      createItemModal: false,
      newItem: {
        type: null,
        grade: null,
        name: null
      },
      rules: {
        itemType: {
          required: true
        },
        itemGrade: {
          required: true
        },
        itemName: {
          required: true,
          min: 2
        }
      },
      loading: false,
      showSnackbar: false
    }),
    computed: {
      ...mapGetters({
        items: 'games/items'
      })
    },
    async created() {
      await this.fetchItems()
    },
    methods: {
      ...mapActions({
        fetchItems: 'games/fetchItems',
        createItem: 'games/createItem'
      }),
      async handleCreateItem() {
        this.loading = true
        await this.createItem(this.newItem)
          .then((() => {
            this.createItemModal = false
            this.showSnackbar = true
          }))
          .finally(() => {
            this.loading = false
          })
      },
      getValidationState({ dirty, validated, valid = null}) {
        return dirty || validated ? !valid : false;
      }
    }
  }
</script>
