export default async (type, name, host = `./${type}`, version) => {
  const publicPaths = {
    development: "/",
    production: `${host}/${name}/`
  };

  const _import = await import("./_import_" + process.env.NODE_ENV);
  let perfree = await _import.default(type, name, host,version);
  return perfree.default(publicPaths[process.env.NODE_ENV]);
};
