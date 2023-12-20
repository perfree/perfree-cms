export default async (type, name,  version) => {
  const publicPaths = {
    development: "/",
    production: `/${type}/${name}/`
  };

  const _import = await import("./_import_" + process.env.NODE_ENV);
  let perfree = await _import.default(type, name, version);
  return perfree.default(publicPaths[process.env.NODE_ENV]);
};
