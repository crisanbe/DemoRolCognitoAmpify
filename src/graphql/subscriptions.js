/* eslint-disable */
// this is an auto generated file. This will be overwritten

export const onCreateUse = /* GraphQL */ `
  subscription OnCreateUse(
    $filter: ModelSubscriptionUseFilterInput
    $usoOwner: String
    $deviceOwner: String
  ) {
    onCreateUse(
      filter: $filter
      usoOwner: $usoOwner
      deviceOwner: $deviceOwner
    ) {
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
export const onUpdateUse = /* GraphQL */ `
  subscription OnUpdateUse(
    $filter: ModelSubscriptionUseFilterInput
    $usoOwner: String
    $deviceOwner: String
  ) {
    onUpdateUse(
      filter: $filter
      usoOwner: $usoOwner
      deviceOwner: $deviceOwner
    ) {
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
export const onDeleteUse = /* GraphQL */ `
  subscription OnDeleteUse(
    $filter: ModelSubscriptionUseFilterInput
    $usoOwner: String
    $deviceOwner: String
  ) {
    onDeleteUse(
      filter: $filter
      usoOwner: $usoOwner
      deviceOwner: $deviceOwner
    ) {
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
export const onCreateDevice = /* GraphQL */ `
  subscription OnCreateDevice(
    $filter: ModelSubscriptionDeviceFilterInput
    $deviceOwner: String
  ) {
    onCreateDevice(filter: $filter, deviceOwner: $deviceOwner) {
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
export const onUpdateDevice = /* GraphQL */ `
  subscription OnUpdateDevice(
    $filter: ModelSubscriptionDeviceFilterInput
    $deviceOwner: String
  ) {
    onUpdateDevice(filter: $filter, deviceOwner: $deviceOwner) {
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
export const onDeleteDevice = /* GraphQL */ `
  subscription OnDeleteDevice(
    $filter: ModelSubscriptionDeviceFilterInput
    $deviceOwner: String
  ) {
    onDeleteDevice(filter: $filter, deviceOwner: $deviceOwner) {
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
export const onCreateCompany = /* GraphQL */ `
  subscription OnCreateCompany(
    $filter: ModelSubscriptionCompanyFilterInput
    $companyOwner: String
  ) {
    onCreateCompany(filter: $filter, companyOwner: $companyOwner) {
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
export const onUpdateCompany = /* GraphQL */ `
  subscription OnUpdateCompany(
    $filter: ModelSubscriptionCompanyFilterInput
    $companyOwner: String
  ) {
    onUpdateCompany(filter: $filter, companyOwner: $companyOwner) {
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
export const onDeleteCompany = /* GraphQL */ `
  subscription OnDeleteCompany(
    $filter: ModelSubscriptionCompanyFilterInput
    $companyOwner: String
  ) {
    onDeleteCompany(filter: $filter, companyOwner: $companyOwner) {
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
export const onCreateBus = /* GraphQL */ `
  subscription OnCreateBus(
    $filter: ModelSubscriptionBusFilterInput
    $busOwner: String
  ) {
    onCreateBus(filter: $filter, busOwner: $busOwner) {
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
export const onUpdateBus = /* GraphQL */ `
  subscription OnUpdateBus(
    $filter: ModelSubscriptionBusFilterInput
    $busOwner: String
  ) {
    onUpdateBus(filter: $filter, busOwner: $busOwner) {
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
export const onDeleteBus = /* GraphQL */ `
  subscription OnDeleteBus(
    $filter: ModelSubscriptionBusFilterInput
    $busOwner: String
  ) {
    onDeleteBus(filter: $filter, busOwner: $busOwner) {
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
