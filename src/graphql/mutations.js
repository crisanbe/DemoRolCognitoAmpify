/* eslint-disable */
// this is an auto generated file. This will be overwritten

export const createUse = /* GraphQL */ `
  mutation CreateUse(
    $input: CreateUseInput!
    $condition: ModelUseConditionInput
  ) {
    createUse(input: $input, condition: $condition) {
      id
      cardNumber
      balance
      sequenceNumber
      status
      usoOwner
      deviceID
      device {
        id
        imei
        civicaSerial
        lastEditorId
        deviceOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceBusId
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceOwner
      __typename
    }
  }
`;
export const updateUse = /* GraphQL */ `
  mutation UpdateUse(
    $input: UpdateUseInput!
    $condition: ModelUseConditionInput
  ) {
    updateUse(input: $input, condition: $condition) {
      id
      cardNumber
      balance
      sequenceNumber
      status
      usoOwner
      deviceID
      device {
        id
        imei
        civicaSerial
        lastEditorId
        deviceOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceBusId
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceOwner
      __typename
    }
  }
`;
export const deleteUse = /* GraphQL */ `
  mutation DeleteUse(
    $input: DeleteUseInput!
    $condition: ModelUseConditionInput
  ) {
    deleteUse(input: $input, condition: $condition) {
      id
      cardNumber
      balance
      sequenceNumber
      status
      usoOwner
      deviceID
      device {
        id
        imei
        civicaSerial
        lastEditorId
        deviceOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceBusId
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceOwner
      __typename
    }
  }
`;
export const createDevice = /* GraphQL */ `
  mutation CreateDevice(
    $input: CreateDeviceInput!
    $condition: ModelDeviceConditionInput
  ) {
    createDevice(input: $input, condition: $condition) {
      id
      imei
      civicaSerial
      lastEditorId
      deviceOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      bus {
        id
        plate
        status
        busOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      uses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceBusId
      __typename
    }
  }
`;
export const updateDevice = /* GraphQL */ `
  mutation UpdateDevice(
    $input: UpdateDeviceInput!
    $condition: ModelDeviceConditionInput
  ) {
    updateDevice(input: $input, condition: $condition) {
      id
      imei
      civicaSerial
      lastEditorId
      deviceOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      bus {
        id
        plate
        status
        busOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      uses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceBusId
      __typename
    }
  }
`;
export const deleteDevice = /* GraphQL */ `
  mutation DeleteDevice(
    $input: DeleteDeviceInput!
    $condition: ModelDeviceConditionInput
  ) {
    deleteDevice(input: $input, condition: $condition) {
      id
      imei
      civicaSerial
      lastEditorId
      deviceOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      bus {
        id
        plate
        status
        busOwner
        companyID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      uses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      deviceBusId
      __typename
    }
  }
`;
export const createCompany = /* GraphQL */ `
  mutation CreateCompany(
    $input: CreateCompanyInput!
    $condition: ModelCompanyConditionInput
  ) {
    createCompany(input: $input, condition: $condition) {
      id
      name
      rutaCivica
      commissionPerTicket
      lastEditorId
      companyOwner
      devices {
        nextToken
        startedAt
        __typename
      }
      buses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
export const updateCompany = /* GraphQL */ `
  mutation UpdateCompany(
    $input: UpdateCompanyInput!
    $condition: ModelCompanyConditionInput
  ) {
    updateCompany(input: $input, condition: $condition) {
      id
      name
      rutaCivica
      commissionPerTicket
      lastEditorId
      companyOwner
      devices {
        nextToken
        startedAt
        __typename
      }
      buses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
export const deleteCompany = /* GraphQL */ `
  mutation DeleteCompany(
    $input: DeleteCompanyInput!
    $condition: ModelCompanyConditionInput
  ) {
    deleteCompany(input: $input, condition: $condition) {
      id
      name
      rutaCivica
      commissionPerTicket
      lastEditorId
      companyOwner
      devices {
        nextToken
        startedAt
        __typename
      }
      buses {
        nextToken
        startedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
export const createBus = /* GraphQL */ `
  mutation CreateBus(
    $input: CreateBusInput!
    $condition: ModelBusConditionInput
  ) {
    createBus(input: $input, condition: $condition) {
      id
      plate
      status
      busOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
export const updateBus = /* GraphQL */ `
  mutation UpdateBus(
    $input: UpdateBusInput!
    $condition: ModelBusConditionInput
  ) {
    updateBus(input: $input, condition: $condition) {
      id
      plate
      status
      busOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
export const deleteBus = /* GraphQL */ `
  mutation DeleteBus(
    $input: DeleteBusInput!
    $condition: ModelBusConditionInput
  ) {
    deleteBus(input: $input, condition: $condition) {
      id
      plate
      status
      busOwner
      companyID
      company {
        id
        name
        rutaCivica
        commissionPerTicket
        lastEditorId
        companyOwner
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        __typename
      }
      createdAt
      updatedAt
      _version
      _deleted
      _lastChangedAt
      __typename
    }
  }
`;
