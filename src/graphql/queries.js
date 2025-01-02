/* eslint-disable */
// this is an auto generated file. This will be overwritten

export const getUse = /* GraphQL */ `
  query GetUse($id: ID!) {
    getUse(id: $id) {
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
export const listUses = /* GraphQL */ `
  query ListUses(
    $filter: ModelUseFilterInput
    $limit: Int
    $nextToken: String
  ) {
    listUses(filter: $filter, limit: $limit, nextToken: $nextToken) {
      items {
        id
        cardNumber
        balance
        sequenceNumber
        status
        usoOwner
        deviceID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceOwner
        __typename
      }
      nextToken
      startedAt
      __typename
    }
  }
`;
export const syncUses = /* GraphQL */ `
  query SyncUses(
    $filter: ModelUseFilterInput
    $limit: Int
    $nextToken: String
    $lastSync: AWSTimestamp
  ) {
    syncUses(
      filter: $filter
      limit: $limit
      nextToken: $nextToken
      lastSync: $lastSync
    ) {
      items {
        id
        cardNumber
        balance
        sequenceNumber
        status
        usoOwner
        deviceID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceOwner
        __typename
      }
      nextToken
      startedAt
      __typename
    }
  }
`;
export const usesByDeviceID = /* GraphQL */ `
  query UsesByDeviceID(
    $deviceID: ID!
    $sortDirection: ModelSortDirection
    $filter: ModelUseFilterInput
    $limit: Int
    $nextToken: String
  ) {
    usesByDeviceID(
      deviceID: $deviceID
      sortDirection: $sortDirection
      filter: $filter
      limit: $limit
      nextToken: $nextToken
    ) {
      items {
        id
        cardNumber
        balance
        sequenceNumber
        status
        usoOwner
        deviceID
        createdAt
        updatedAt
        _version
        _deleted
        _lastChangedAt
        deviceOwner
        __typename
      }
      nextToken
      startedAt
      __typename
    }
  }
`;
export const getDevice = /* GraphQL */ `
  query GetDevice($id: ID!) {
    getDevice(id: $id) {
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
export const listDevices = /* GraphQL */ `
  query ListDevices(
    $filter: ModelDeviceFilterInput
    $limit: Int
    $nextToken: String
  ) {
    listDevices(filter: $filter, limit: $limit, nextToken: $nextToken) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const syncDevices = /* GraphQL */ `
  query SyncDevices(
    $filter: ModelDeviceFilterInput
    $limit: Int
    $nextToken: String
    $lastSync: AWSTimestamp
  ) {
    syncDevices(
      filter: $filter
      limit: $limit
      nextToken: $nextToken
      lastSync: $lastSync
    ) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const devicesByCompanyID = /* GraphQL */ `
  query DevicesByCompanyID(
    $companyID: ID!
    $sortDirection: ModelSortDirection
    $filter: ModelDeviceFilterInput
    $limit: Int
    $nextToken: String
  ) {
    devicesByCompanyID(
      companyID: $companyID
      sortDirection: $sortDirection
      filter: $filter
      limit: $limit
      nextToken: $nextToken
    ) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const getCompany = /* GraphQL */ `
  query GetCompany($id: ID!) {
    getCompany(id: $id) {
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
export const listCompanies = /* GraphQL */ `
  query ListCompanies(
    $filter: ModelCompanyFilterInput
    $limit: Int
    $nextToken: String
  ) {
    listCompanies(filter: $filter, limit: $limit, nextToken: $nextToken) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const syncCompanies = /* GraphQL */ `
  query SyncCompanies(
    $filter: ModelCompanyFilterInput
    $limit: Int
    $nextToken: String
    $lastSync: AWSTimestamp
  ) {
    syncCompanies(
      filter: $filter
      limit: $limit
      nextToken: $nextToken
      lastSync: $lastSync
    ) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const getBus = /* GraphQL */ `
  query GetBus($id: ID!) {
    getBus(id: $id) {
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
export const listBuses = /* GraphQL */ `
  query ListBuses(
    $filter: ModelBusFilterInput
    $limit: Int
    $nextToken: String
  ) {
    listBuses(filter: $filter, limit: $limit, nextToken: $nextToken) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const syncBuses = /* GraphQL */ `
  query SyncBuses(
    $filter: ModelBusFilterInput
    $limit: Int
    $nextToken: String
    $lastSync: AWSTimestamp
  ) {
    syncBuses(
      filter: $filter
      limit: $limit
      nextToken: $nextToken
      lastSync: $lastSync
    ) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
export const busesByCompanyID = /* GraphQL */ `
  query BusesByCompanyID(
    $companyID: ID!
    $sortDirection: ModelSortDirection
    $filter: ModelBusFilterInput
    $limit: Int
    $nextToken: String
  ) {
    busesByCompanyID(
      companyID: $companyID
      sortDirection: $sortDirection
      filter: $filter
      limit: $limit
      nextToken: $nextToken
    ) {
      items {
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
      nextToken
      startedAt
      __typename
    }
  }
`;
