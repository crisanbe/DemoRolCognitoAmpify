// @ts-check
import { initSchema } from '@aws-amplify/datastore';
import { schema } from './schema';



const { Use, Device, Company, Bus } = initSchema(schema);

export {
  Use,
  Device,
  Company,
  Bus
};