import { ModelInit, MutableModel, __modelMeta__, ManagedIdentifier } from "@aws-amplify/datastore";
// @ts-ignore
import { LazyLoading, LazyLoadingDisabled, AsyncItem, AsyncCollection } from "@aws-amplify/datastore";





type EagerUse = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Use, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly cardNumber?: string | null;
  readonly balance?: number | null;
  readonly sequenceNumber?: number | null;
  readonly status?: boolean | null;
  readonly usoOwner?: string | null;
  readonly deviceID: string;
  readonly device?: Device | null;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

type LazyUse = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Use, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly cardNumber?: string | null;
  readonly balance?: number | null;
  readonly sequenceNumber?: number | null;
  readonly status?: boolean | null;
  readonly usoOwner?: string | null;
  readonly deviceID: string;
  readonly device: AsyncItem<Device | undefined>;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

export declare type Use = LazyLoading extends LazyLoadingDisabled ? EagerUse : LazyUse

export declare const Use: (new (init: ModelInit<Use>) => Use) & {
  copyOf(source: Use, mutator: (draft: MutableModel<Use>) => MutableModel<Use> | void): Use;
}

type EagerDevice = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Device, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly imei?: string | null;
  readonly civicaSerial?: string | null;
  readonly lastEditorId?: number | null;
  readonly deviceOwner?: string | null;
  readonly companyID: string;
  readonly company?: Company | null;
  readonly bus?: Bus | null;
  readonly uses?: (Use | null)[] | null;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
  readonly deviceBusId?: string | null;
}

type LazyDevice = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Device, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly imei?: string | null;
  readonly civicaSerial?: string | null;
  readonly lastEditorId?: number | null;
  readonly deviceOwner?: string | null;
  readonly companyID: string;
  readonly company: AsyncItem<Company | undefined>;
  readonly bus: AsyncItem<Bus | undefined>;
  readonly uses: AsyncCollection<Use>;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
  readonly deviceBusId?: string | null;
}

export declare type Device = LazyLoading extends LazyLoadingDisabled ? EagerDevice : LazyDevice

export declare const Device: (new (init: ModelInit<Device>) => Device) & {
  copyOf(source: Device, mutator: (draft: MutableModel<Device>) => MutableModel<Device> | void): Device;
}

type EagerCompany = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Company, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly name?: string | null;
  readonly rutaCivica?: string | null;
  readonly commissionPerTicket?: number | null;
  readonly lastEditorId?: number | null;
  readonly companyOwner?: string | null;
  readonly devices?: (Device | null)[] | null;
  readonly buses?: (Bus | null)[] | null;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

type LazyCompany = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Company, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly name?: string | null;
  readonly rutaCivica?: string | null;
  readonly commissionPerTicket?: number | null;
  readonly lastEditorId?: number | null;
  readonly companyOwner?: string | null;
  readonly devices: AsyncCollection<Device>;
  readonly buses: AsyncCollection<Bus>;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

export declare type Company = LazyLoading extends LazyLoadingDisabled ? EagerCompany : LazyCompany

export declare const Company: (new (init: ModelInit<Company>) => Company) & {
  copyOf(source: Company, mutator: (draft: MutableModel<Company>) => MutableModel<Company> | void): Company;
}

type EagerBus = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Bus, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly plate?: string | null;
  readonly status?: boolean | null;
  readonly busOwner?: string | null;
  readonly companyID: string;
  readonly company?: Company | null;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

type LazyBus = {
  readonly [__modelMeta__]: {
    identifier: ManagedIdentifier<Bus, 'id'>;
    readOnlyFields: 'createdAt' | 'updatedAt';
  };
  readonly id: string;
  readonly plate?: string | null;
  readonly status?: boolean | null;
  readonly busOwner?: string | null;
  readonly companyID: string;
  readonly company: AsyncItem<Company | undefined>;
  readonly createdAt?: string | null;
  readonly updatedAt?: string | null;
}

export declare type Bus = LazyLoading extends LazyLoadingDisabled ? EagerBus : LazyBus

export declare const Bus: (new (init: ModelInit<Bus>) => Bus) & {
  copyOf(source: Bus, mutator: (draft: MutableModel<Bus>) => MutableModel<Bus> | void): Bus;
}